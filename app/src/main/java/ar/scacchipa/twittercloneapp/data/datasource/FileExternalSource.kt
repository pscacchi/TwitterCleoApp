package ar.scacchipa.twittercloneapp.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class FileExternalSource(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getFile(urlString: String): ByteArray {
        return withContext(ioDispatcher) {
            URL(urlString).openStream().readBytes()
        }
    }
}