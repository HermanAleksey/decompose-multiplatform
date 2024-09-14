package com.justparokq.homeftp.shared.ftp.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.models.Result
import com.justparokq.homeftp.shared.componentCoroutineScope
import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapper
import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapperImpl
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClient
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClientImpl
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.FtpExplorerScreenModel
import kotlinx.coroutines.launch

class DefaultFtpExplorerComponent(
    componentContext: ComponentContext,
    private val ftpHttpClient: FtpCommunicationHttpClient = FtpCommunicationHttpClientImpl(),
    private val fileSystemObjectMapper: FileSystemObjectMapper = FileSystemObjectMapperImpl(),
) : FtpExplorerComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    private val _state = MutableValue(FtpExplorerScreenModel())
    override val state: Value<FtpExplorerScreenModel> = _state

    init {
        val serverFtpRootUri = ""
        load(serverFtpRootUri)
    }

    private fun load(uriToLoad: String) {
        coroutineScope.launch {
            ftpHttpClient.getDirectoryContent(uriToLoad)
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _state.update {
                                it.copy(isLoading = result.loading)
                            }
                        }

                        is Result.Error -> {
                            _state.update {
                                it.copy(isLoading = false)
                            }
                            println("DefaultFtpExplorerComponent; Show error: ${result.errorMessage}")
                        }

                        is Result.Success -> {
                            val domainObjects = result.result.map {
                                fileSystemObjectMapper.map(it)
                            }
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    fileTree = it.fileTree.copy(
                                        content = domainObjects
                                    )
                                )
                            }
                        }
                    }
                }
        }

    }

    override fun onDirectoryClicked(dirPath: List<String>) {
        TODO("Not yet implemented")
    }

    override fun onFileClicked(file: FileSystemObject.File) {
        TODO("Not yet implemented")
    }

    override fun onToggleHierarchyView() {
        TODO("Not yet implemented")
    }
}
