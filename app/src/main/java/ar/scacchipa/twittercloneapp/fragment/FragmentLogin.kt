package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.DialogLoginErrorBinding
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginLayoutBinding
import ar.scacchipa.twittercloneapp.viewmodel.LoginViewModel


class FragmentLogin : Fragment() {

    private var mainBinding: FragmentLoginLayoutBinding? = null
    private var errorBinding: DialogLoginErrorBinding? = null
    private var errorDialog: AlertDialog? = null

    private val viewModel: LoginViewModel by viewModels()
    private val args: FragmentLoginArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.mustShowErrorMsg(args.showErrorMsg)

        mainBinding = FragmentLoginLayoutBinding.inflate(inflater)
        mainBinding?.buttonLogin?.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentLogin_to_fragmentLoginAuthWebDialog)
        }

        createDialog(inflater)

        viewModel.addMsgChangeObserver(viewLifecycleOwner) { mustShowErrorMsg ->
            if (mustShowErrorMsg) {
                this.showErrorDialog()
            } else {
                this.hideErrorDialog()
            }
        }

        return mainBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainBinding = null
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

        mainBinding?.root?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondary_background))TH
    }

    private fun hideErrorDialog() {
        errorDialog?.hide()
        mainBinding?.root?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_background))
    }
}