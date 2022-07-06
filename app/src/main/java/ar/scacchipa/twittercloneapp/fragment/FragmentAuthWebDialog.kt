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
import ar.scacchipa.twittercloneapp.viewmodel.AuthWebDialogViewModel

class FragmentAuthWebDialog : Fragment() {

    private val viewModel: AuthWebDialogViewModel by viewModels()
    private var binding: FragmentAuthWebDialogLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAuthWebDialogLayoutBinding.inflate(inflater)

        viewModel.userAccessToken.observe(viewLifecycleOwner) {
            if (it.accessToken != "") {
                val action = FragmentAuthWebDialogDirections
                    .actionFragmentAuthWebDialogToFragmentHome(it.accessToken)
                findNavController().navigate(action)
            } else {
                findNavController().navigate(R.id.action_fragmentAuthWebDialog_to_fragmentLoginError)
            }
        }

        binding?.loginApiWebview?.apply {
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
                        viewModel.controlRequest(uri)
                    }
                    return super.shouldOverrideUrlLoading(view, request)
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