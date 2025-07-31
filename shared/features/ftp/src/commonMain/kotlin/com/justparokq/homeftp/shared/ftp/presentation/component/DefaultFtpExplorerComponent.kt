package com.justparokq.homeftp.shared.ftp.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponentIntent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerScreenModel
import com.justparokq.homeftp.shared.ftp.api.OnDirectoryClicked
import com.justparokq.homeftp.shared.ftp.api.OnEndOfPageReached
import com.justparokq.homeftp.shared.ftp.api.OnFileSystemObjectClicked
import com.justparokq.homeftp.shared.ftp.api.OnFilesPicked
import com.justparokq.homeftp.shared.ftp.api.OnFloatingButtonClicked
import com.justparokq.homeftp.shared.ftp.api.OnNavigateBackClicked
import com.justparokq.homeftp.shared.ftp.api.OnRefreshPulled
import com.justparokq.homeftp.shared.ftp.api.OnRetryButtonClicked
import com.justparokq.homeftp.shared.ftp.api.OnScreenOpened
import com.justparokq.homeftp.shared.ftp.api.OnSortingApplyClicked
import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapper
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClient
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.PAGE_SIZE
import com.justparokq.homeftp.shared.ftp.model.PaginationState
import com.justparokq.homeftp.shared.ftp.model.Path
import com.justparokq.homeftp.shared.navigation.acrhitecture.InitHelper
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DefaultFtpExplorerComponent(
    componentContext: ComponentContext,
    private val ftpHttpClient: FtpCommunicationHttpClient,
    private val fileSystemObjectMapper: FileSystemObjectMapper,
    private val featureNavigator: FeatureNavigator,
    initHelper: InitHelper,
) : FtpExplorerComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    // Start job immediately, in case first `OnEndOfPageReached` triggers before loading started
    private var firstLoadingSafety: Job? = Job().apply {
        this.start()
    }
    private var loadDataJob: Job? = null

    private val _state = MutableValue(FtpExplorerScreenModel())
    override val state: Value<FtpExplorerScreenModel> = _state

    init {
        initHelper(intentProcessor = ::processIntent, intent = OnScreenOpened)
    }

    override fun processIntent(intent: FtpExplorerComponentIntent) {
        println("Incoming intent: $intent")
        when (intent) {
            OnScreenOpened -> loadDataFromPath()
            is OnDirectoryClicked -> onDirectoryClicked(intent.dirPath)
            is OnFileSystemObjectClicked -> onFileSystemObjectClicked(intent.fsObject)
            is OnFilesPicked -> Unit // TODO()
            OnSortingApplyClicked -> Unit // TODO()
            is OnFloatingButtonClicked -> Unit // TODO()
            OnRefreshPulled -> loadPage()
            OnNavigateBackClicked -> onNavigateBackClicked()
            OnEndOfPageReached -> loadNextPage()
            OnRetryButtonClicked -> loadPage()
        }
    }

    private fun onDirectoryClicked(dirPath: Path) {
        _state.update {
            it.copy(
                currentPath = dirPath,
                paginationState = PaginationState()
            )
        }
        loadDataFromPath()
    }

    private fun onFileSystemObjectClicked(fsObject: FileSystemObject) {
        when (fsObject) {
            is FileSystemObject.Directory -> {
                onDirectoryClicked(fsObject.fullPath)
            }

            is FileSystemObject.File.Image -> {
                // todo open full screen photo
            }

            FileSystemObject.File.Unknown -> {
                // todo show toast
            }

            is FileSystemObject.File.Video -> {
                // todo open full screen video
            }
        }
    }

    private fun loadDataFromPath() {
        // cancel active loading when navigating to the another directory
        loadDataJob?.cancel()

        _state.update {
            it.copy(
                paginationState = PaginationState(),
                fsObjects = emptyList()
            )
        }

        loadPage()
        firstLoadingSafety?.cancel()
    }

    private fun loadNextPage() = coroutineScope.launch {
        // if new page is already loading - wait for it and only then send new request
        val isLoadingNextPage = _state.value.paginationState.isLoadingNextPage
                && _state.value.paginationState.hasNextPage
        val isLoadingFirstPage = _state.value.isLoading
        val noMorePagesToLoad = _state.value.paginationState.hasNextPage.not()
        if (isLoadingNextPage || isLoadingFirstPage) {
            firstLoadingSafety?.join()
            loadDataJob?.join()
        } else if (noMorePagesToLoad) {
            return@launch
        }

        loadPage()
    }

    private fun loadPage() = coroutineScope.launch {
        val currentPage = _state.value.paginationState.currentPage
        val path = _state.value.currentPath
        val isLoadingFirstPage = currentPage == 0

        loadDataJob = coroutineScope.launch {
            ftpHttpClient.getDirectoryContent(path.raw, currentPage, PAGE_SIZE)
                .collect { result ->
                    println("Collect: $result")
                    when (result) {
                        is Result.Loading -> {
                            _state.update {
                                if (isLoadingFirstPage)
                                    it.copy(
                                        isLoading = result.loading,
                                        error = null,
                                        paginationState = it.paginationState.copy(
                                            error = null
                                        )
                                    )
                                else it.copy(
                                    error = null,
                                    paginationState = it.paginationState.copy(
                                        isLoadingNextPage = result.loading,
                                        error = null,
                                    )
                                )
                            }
                        }

                        is Result.Error -> {
                            _state.update { model ->
                                if (isLoadingFirstPage)
                                    model.copy(
                                        error = result.errorMessage,
                                        paginationState = model.paginationState.copy(
                                            hasNextPage = false
                                        )
                                    )
                                else model.copy(
                                    paginationState = model.paginationState.copy(
                                        error = result.errorMessage
                                    )
                                )
                            }
                        }

                        is Result.Success -> {
                            val response = result.result
                            val newItems = response.files.map {
                                fileSystemObjectMapper.toFileSystemObject(it)
                            }

                            _state.update {
                                val newItemsList = if (isLoadingFirstPage)
                                    newItems else it.fsObjects + newItems

                                it.copy(
                                    fsObjects = newItemsList,
                                    paginationState = PaginationState(
                                        currentPage = currentPage + 1,
                                        hasNextPage = response.hasNextPage,
                                    )
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun onNavigateBackClicked() {
        val parentDirectory = _state.value.currentPath.parent()
        if (parentDirectory == null) {
            // we are at the root, leave screen
            featureNavigator.popBackStack()
        } else {
            onDirectoryClicked(parentDirectory)
        }
    }

    override fun processBackInput(): Boolean {
        onNavigateBackClicked()
        return true
    }
}
