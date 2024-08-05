package com.justparokq.homeftp.shared.ftp.presentation

import com.arkivanov.decompose.ComponentContext
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject

class DefaultFtpExplorerComponent(
    componentContext: ComponentContext,
) : FtpExplorerComponent, ComponentContext by componentContext {

    init {
        /**
         * Initially load data from server root
         * By convention all user data on server available for reading\writing
         * stored in `/storage` directory
         * User can interact with all inner directories and files
         * */
        val serverFtpRootUri = "/storage"
        load(serverFtpRootUri)
    }

    private fun load(uriToLoad: String) {

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
