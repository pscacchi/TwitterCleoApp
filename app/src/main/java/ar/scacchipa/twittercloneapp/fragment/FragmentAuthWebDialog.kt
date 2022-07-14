package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.databinding.FragmentAuthWebDialogLayoutBinding
import ar.scacchipa.twittercloneapp.repository.Constants
import ar.scacchipa.twittercloneapp.viewmodel.AuthWebDialogViewModel
import java.net.URI

class FragmentAuthWebDialog : Fragment() {

    private val viewModel: AuthWebDialogViewModel by viewModels()
    private var binding: FragmentAuthWebDialogLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAuthWebDialogLayoutBinding.inflate(inflater)

        viewModel.userAccessToken.observe(viewLifecycleOwner) {
            when {
                it.error == Constants.ERROR_CANCELLED_AUTH -> {
                    findNavController().navigateUp()
                }
                it.error == Constants.ERROR_NO_AUTHORIZATION -> {
                    val action= FragmentAuthWebDialogDirections
                        .actionFragmentLoginAuthWebDialogToFragmentLogin(true)
                    findNavController().navigate(action)
                }
                it.accessToken != "" -> {
                    val action = FragmentAuthWebDialogDirections
                        .actionFragmentAuthWebDialogToFragmentHome(it.accessToken)
                    findNavController().navigate(action)
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
                        Log.i("WebView", uri.toString())
                        viewModel.onReceiveUrl(URI(uri.toString()))
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    viewModel.onErrorAuthorization(request, errorResponse)
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