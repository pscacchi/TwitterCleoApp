package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginApiLayoutBinding


class FragmentLoginApi : Fragment() {

    private var binding: FragmentLoginApiLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginApiLayoutBinding.inflate(inflater)

        binding?.loginApiWebview?.let { webView ->
            webView.settings.javaScriptEnabled = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.domStorageEnabled = true
            webView.settings.loadsImagesAutomatically = true
            webView.settings.allowContentAccess = true
            webView.settings.allowFileAccess = true

            webView.webViewClient = WebViewClient()

            webView.loadUrl("https://twitter.com/i/oauth2/authorize?response_type=code&client_id=Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ&redirect_uri=https://twittercloneendava.firebaseapp.com/__/auth/handler&scope=users.read%20tweet.read%20offline.access%20list.read%20follows.read%20like.read%20space.read%20tweet.write%20like.write&state=state&code_challenge=challenge&code_challenge_method=plain")
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}