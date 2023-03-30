package com.example.rickandmorty.ui.pages.popup

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.constants.Constants
import com.example.rickandmorty.databinding.FragmentBottomSheetBinding
import com.example.rickandmorty.ui.pages.popup.adapter.PopUpAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetBinding
    private lateinit var progressDialog: Dialog
    private val bottomSheetViewModel: BottomSheetViewModel by viewModels()
    private var popUpAdapter = PopUpAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun getTheme(): Int {
        return R.style.Theme_Design_BottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getId = requireArguments().getInt("id")
        val getSeasonNum = requireArguments().getString("season")
        binding.recyclerViewCharacters.adapter = popUpAdapter


        when (requireArguments().getInt("type")) {

            /** if type comes from episode it binds episode items */

            Constants.typeEpisode -> {

                bottomSheetViewModel.fetchEpisodeById(getId)

                lifecycleScope.launchWhenStarted {

                    lifecycleScope.launchWhenStarted {
                        bottomSheetViewModel._progressStateFlow.collectLatest { showProgress ->
                            if (showProgress) {
                                showLoadingProgressForBottom()
                            } else {
                                dismissLoadingProgressForBottom()
                            }
                        }
                    }

                    bottomSheetViewModel._episodeByIdStateFlow.collectLatest {
                        it?.let {
                            binding.popUpStaticCharacters.text = getString(R.string.characters)
                            binding.popUpEpisodeName.text = it.name
                            binding.popUpSeasonTxt.text = it.airDate
                            binding.popUpDate.text = getSeasonNum
                            val characterList = it.characters?.map { char ->
                                char.substring(startIndex = char.lastIndexOf("/") + 1)
                            }
                            if (!characterList.isNullOrEmpty()) {
                                bottomSheetViewModel.fetchGetMoreCharactersThanOne(characterList)
                            }
                        }
                    }
                }
                printImageAndActiveNavigation()
            }

            /** if type comes from location it binds episode items */

            Constants.typeLocation -> {

                bottomSheetViewModel.fetchLocationById(getId)

                lifecycleScope.launchWhenStarted {
                    bottomSheetViewModel._progressStateFlow.collectLatest { showProgress ->
                        if (showProgress) {
                            showLoadingProgressForBottom()
                        } else {
                            dismissLoadingProgressForBottom()
                        }
                    }
                }
                lifecycleScope.launchWhenStarted {
                    bottomSheetViewModel._locationByIdStateFlow.collectLatest {
                        it?.let {
                            binding.popUpSeasonTxt.text = getString(R.string.dimensions_static_text)
                            binding.popUpStaticCharacters.text = getString(R.string.Residents)
                            binding.popUpEpisodeName.text = it.name
                            binding.popUpDate.text = it.dimension
                            val residentsOfPlanet = it.residents?.map { char ->
                                /** residents is a full request ,Only numbers taken for Bottom Sheet Fragment  */
                                char.substring(startIndex = char.lastIndexOf("/") + 1)
                            }
                            if (!residentsOfPlanet.isNullOrEmpty()) {
                                bottomSheetViewModel.fetchGetMoreCharactersThanOne(residentsOfPlanet)
                            } else {
                                binding.noOneIsHere.text = getString(R.string.nobody_lives_text)
                                binding.recyclerViewCharacters.visibility = View.GONE
                                binding.noOneIsHere.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                printImageAndActiveNavigation()
            }
        }
    }

    /** Bottom Sheet Fragment - "List of image requests" and navigation to detail */
    private fun printImageAndActiveNavigation() {
        lifecycleScope.launchWhenStarted {
            bottomSheetViewModel._progressStateFlow.collectLatest { showProgress ->
                if (showProgress) {
                    showLoadingProgressForBottom()
                } else {
                    dismissLoadingProgressForBottom()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            bottomSheetViewModel._characterFromLocationStateFlow.collectLatest { characterResponse ->
                characterResponse.let {
                    it?.let { its -> popUpAdapter.addPopUpList(its) }
                }
            }
        }
        popUpAdapter.clickCharacter = { clickedItem ->
            val bundle = Bundle()
            clickedItem.id?.let { it -> bundle.putInt("detailId", it) }
            findNavController().navigate(R.id.detailFragment, bundle)
        }
    }


    private fun showLoadingProgressForBottom() {
        if (!::progressDialog.isInitialized) {
            progressDialog = Dialog(requireContext())
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog.setContentView(R.layout.loading_progress)
            progressDialog.setCancelable(false)
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.show()
    }
    private fun dismissLoadingProgressForBottom() {
        if (::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}


