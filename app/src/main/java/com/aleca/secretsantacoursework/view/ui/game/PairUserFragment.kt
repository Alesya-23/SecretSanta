package com.aleca.secretsantacoursework.view.ui.game

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.databinding.FragmentPairUserBinding
import com.aleca.secretsantacoursework.viewmodel.DetailsGameViewModel

private const val ID_ARGUMENT = "ID_ARGUMENT"

class PairUserFragment : Fragment() {
    private lateinit var pairUserBinding: FragmentPairUserBinding
    private val pairIdArgument by lazy {
        requireArguments().getInt(ID_ARGUMENT, -1)
    }

    private val viewModel: DetailsGameViewModel by viewModels()

    private fun getUser() {
        val user = activity?.applicationContext?.let { viewModel.getUser(pairIdArgument, it) }
        if (user != null) {
            pairUserBinding.nameUser.text = user.name
            pairUserBinding.descriptionHobbyUser.text = user.hobbies
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pairUserBinding = FragmentPairUserBinding.bind(view)
        getUser()
        requireActivity().title = getString(R.string.title_game_pair)
    }

    companion object {
        fun newInstance(userId: Int) = PairUserFragment().apply {
            this.arguments = bundleOf(
                ID_ARGUMENT to userId
            )
        }
    }
}