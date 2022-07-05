package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentAuthWebDialogLayoutBinding
import ar.scacchipa.twittercloneapp.viewmodel.LoginApiViewModel


class FragmentAuthWebDialog : Fragment() {

    private val viewModel: LoginApiViewModel by viewModels()
    private var binding: FragmentAuthWebDialogLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val clientId = getString(R.string.client_id)
        val redirectUri = getString(R.string.redirect_uri)

        binding = FragmentAuthWebDialogLayoutBinding.inflate(inflater)

        viewModel.userAccessToken.observe(viewLifecycleOwner) {
            if (it.access_token != "") {
                val action = FragmentAuthWebDialogDirections
                    .actionFragmentAuthWebDialogToFragmentHome(it.access_token)
                findNavController().navigate(action)
            } else {
                findNavController().navigate(R.id.action_fragmentAuthWebDialog_to_fragmentLoginError)
            }
        }

        binding?.loginApiWebview?.let { webView ->
            webView.settings.javaScriptEnabled = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.domStorageEnabled = true
            webView.settings.loadsImagesAutomatically = true
            webView.settings.allowContentAccess = true
            webView.settings.allowFileAccess = true

            webView.webViewClient = object: WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.let { uri ->
                        Log.i("WebView", uri.toString())
                        viewModel.controlRequest(uri, clientId, redirectUri)
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }

            val url = viewModel.createTemporaryCodeUrl(clientId, redirectUri)
            webView.loadUrl(url)
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}