package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.DialogLoginErrorBinding
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginLayoutBinding


class FragmentLogin : Fragment() {

    private var mainBindind: FragmentLoginLayoutBinding? = null
    private var errorBinding: DialogLoginErrorBinding? = null
    private var errorDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainBindind = FragmentLoginLayoutBinding.inflate(inflater)
        mainBindind?.buttonLogin?.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentLoginAuthWebDialog)
        }

        createDialog(inflater)

        showErrorDialog()
        return mainBindind?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainBindind = null
        errorBinding = null
        errorDialog = null
    }

    private fun createDialog(inflater: LayoutInflater) {
        errorBinding = DialogLoginErrorBinding.inflate(inflater)
        errorDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setView(errorBinding?.root)
            .setCancelable(false)
            .create()
    }
    private fun showErrorDialog() {
        errorDialog?.show()
        errorDialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        errorBinding?.okLoginErrorButton?.setOnClickListener {
            hideErrorDialog()
        }
    }

    private fun hideErrorDialog() {
        errorDialog?.hide()
    }
}