package ar.scacchipa.twittercloneapp.presentation.login

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
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginWebSectionLayoutBinding
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.presentation.MainActivity
import ar.scacchipa.twittercloneapp.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URI

class FragmentLoginWebSection : Fragment() {

    private val viewModel: LoginWebSectionViewModel by viewModel()
    private var binding: FragmentLoginWebSectionLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginWebSectionLayoutBinding.inflate(inflater)

        viewModel.responseDomain.observe(viewLifecycleOwner) { token ->
            when (token) {
                is ResponseDomain.Success<*> -> {
                    val intent = Intent(
                        activity,
                        MainActivity::class.java
                    )
                    intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity( intent )
                }
                is ResponseDomain.Error -> {
                    val action = FragmentLoginWebSectionDirections
                        .actionFragmentLoginWebSectionToFragmentLogin(true)
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

            loadUrl(getConsumableAuthCode())
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getConsumableAuthCode(): String {
        return "https://twitter.com/i/oauth2/authorize?" +
                "response_type=${Constants.RESPONSE_TYPE_CODE}&" +
                "client_id=${Constants.CLIENT_ID}&" +
                "redirect_uri=${Constants.REDIRECT_URI}&" +
                "scope=${getScope()}&" +
                "state=${Constants.STATE_STATE}&" +
                "code_challenge=${Constants.CODE_CHALLENGE_CHALLENGE}&" +
                "code_challenge_method=${Constants.CODE_CHALLENGE_METHOD_PLAIN}"
    }

    private fun getScope(): String {
        return listOf(
            Constants.SCOPE_USERS_READ,
            Constants.SCOPE_TWEET_READ,
            Constants.SCOPE_TWEET_WRITE,
            Constants.SCOPE_OFFLINE_ACCESS,
            Constants.SCOPE_LIST_READ,
            Constants.SCOPE_FOLLOWS_READ,
            Constants.SCOPE_LIKE_READ,
            Constants.SCOPE_LIKE_WRITE,
            Constants.SCOPE_SPACE_READ
        ).joinToString("%20")
    }


}