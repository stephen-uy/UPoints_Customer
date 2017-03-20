package com.android.stephen.upoints_customer.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.stephen.upoints_customer.R;
import com.android.stephen.upoints_customer.databinding.FragmentProfileBinding;
import com.android.stephen.upoints_customer.model.CustomerModel;
import com.android.stephen.upoints_customer.utils.GlobalVariables;
import com.android.stephen.upoints_customer.utils.Helper;

import java.text.ParseException;

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnProfileFragmentInteractionListener mListener;
    public static CustomerModel customerModel;
    FragmentProfileBinding fragmentProfileBinding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(getResources().getString(R.string.title_profile));
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,
                container, false);
        setUpProfile();
        return fragmentProfileBinding.getRoot();
    }

    private void setUpProfile() {
        fragmentProfileBinding.imgProfilePic.setImageBitmap(Helper.decodeBase64(customerModel.getPicture()));
        fragmentProfileBinding.tvCustID.setText(customerModel.getCustomerID());
        fragmentProfileBinding.tvName.setText(customerModel.getFirstName() + " "+ customerModel.getMiddleName() + " " + customerModel.getLastName());
        fragmentProfileBinding.tvEmail.setText(customerModel.getEmailAddress());
        try {
            fragmentProfileBinding.tvBirthdate.setText(Helper.formatDate(GlobalVariables.dateTimeFormat, GlobalVariables.dateFormat, customerModel.getBirthDate()));
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        fragmentProfileBinding.tvMobile.setText(customerModel.getMobileNumber());
        fragmentProfileBinding.tvTotalUPoints.setText(customerModel.getTotalPoints());
        fragmentProfileBinding.tvTotalRemainingUPoints.setText(customerModel.getRemainingPoints());
        fragmentProfileBinding.tvTotalWDPoints.setText(customerModel.getWithdrawPoints());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProfileFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileFragmentInteractionListener) {
            mListener = (OnProfileFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnProfileFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProfileFragmentInteraction(Uri uri);
    }
}
