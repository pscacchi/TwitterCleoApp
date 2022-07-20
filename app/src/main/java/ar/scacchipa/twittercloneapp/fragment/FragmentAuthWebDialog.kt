package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.databinding.FragmentAuthWebDialogLayoutBinding
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

        viewModel.userAccessToken.observe(viewLifecycleOwner) { token ->
            when {
                token.isCancelationErrorToken() -> {
                    findNavController().navigateUp()
                }
                token.isHostLookupErrorToken()-> {
                    val action= FragmentAuthWebDialogDirections
                        .actionFragmentLoginAuthWebDialogToFragmentLogin(true)
                    findNavController().navigate(action)
                }
                token.isSuccessToken() -> {
                    val action = FragmentAuthWebDialogDirections
                        .actionFragmentAuthWebDialogToFragmentHome(token.accessToken)
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