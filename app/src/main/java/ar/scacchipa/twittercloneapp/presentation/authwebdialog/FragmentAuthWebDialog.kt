package ar.scacchipa.twittercloneapp.presentation.authwebdialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.presentation.MainCloneActivity
import ar.scacchipa.twittercloneapp.data.repository.ResponseDomain
import ar.scacchipa.twittercloneapp.databinding.FragmentAuthWebDialogLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URI

class FragmentAuthWebDialog : Fragment() {

    private val viewModel: AuthWebDialogViewModel by viewModel()
    private var binding: FragmentAuthWebDialogLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAuthWebDialogLayoutBinding.inflate(inflater)

        viewModel.responseDomain.observe(viewLifecycleOwner) { token ->
            when (token) {

                is ResponseDomain.Success<*> -> {
                    val intent = Intent(
                        activity,
                        MainCloneActivity::class.java
                    )
                    intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity( intent )
                }
                is ResponseDomain.Error -> {
                    val action = FragmentAuthWebDialogDirections
                        .actionFragmentLoginAuthWebDialogToFragmentLogin(true)
                    findNavController().navigate(action)
                }
                is ResponseDomain.Cancel,
                is ResponseDomain.Exception -> {
                    findNavController().navigateUp()
                }
                else -> {
                    findNavController().navigateUp()
                }
            }
        }

        binding?.loginWebview?.apply {
            settings.apply {
                javaScriptEnabled = true
                loadWithOverviewMode = true
                domStorageEnabled = true
                loadsImagesAutomatically = true
                allowContentAccess = true
                allowFileAccess = true
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.let { uri ->
                        viewModel.onReceiveUrl(URI(uri.toString()))
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    viewModel.onReceivedWebError(error)
                }
            }

            loadUrl(viewModel.createTemporaryCodeUrl())
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}