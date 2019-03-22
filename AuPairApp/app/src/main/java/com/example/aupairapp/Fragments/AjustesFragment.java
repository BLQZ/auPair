package com.example.aupairapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Generator.UtilUser;
import com.example.aupairapp.Model.PassDto;
import com.example.aupairapp.Model.User;
import com.example.aupairapp.R;
import com.example.aupairapp.Services.AuthService;
import com.example.aupairapp.SessionActivity;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AjustesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AjustesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AjustesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView tvNombre, tvEmail;
    private EditText etPass, etNewPass, etRepeatPass;
    private ImageView ivImagen;
    private Button btnLogout,btnCambiarPass, btnCambiar;

    public AjustesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AjustesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AjustesFragment newInstance(String param1, String param2) {
        AjustesFragment fragment = new AjustesFragment();
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
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        tvNombre = view.findViewById(R.id.textViewNombrePerfil);
        tvEmail = view.findViewById(R.id.textViewEmailPerfil);
        ivImagen = view.findViewById(R.id.imageViewPerfil);
        btnLogout = view.findViewById(R.id.buttonLogout);
        btnCambiarPass = view.findViewById(R.id.buttonCambiarPass);
        btnCambiar = view.findViewById(R.id.buttonUpdatePass);
        etPass = view.findViewById(R.id.editTextPassPerfil);
        etNewPass = view.findViewById(R.id.editTextNuevaPassPerfil);
        etRepeatPass = view.findViewById(R.id.editTextRepetirPass);

        btnCambiar.setVisibility(View.GONE);
        etPass.setVisibility(View.GONE);
        etNewPass.setVisibility(View.GONE);
        etRepeatPass.setVisibility(View.GONE);

        tvNombre.setText(UtilUser.getNombre(getActivity()));
        tvEmail.setText(UtilUser.getEmail(getActivity()));

        Glide
                .with(getActivity())
                .load(UtilUser.getImagen(getActivity()))
                .into(ivImagen);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                navegarSessionActivity();
            }
        });

        btnCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCambiar.setVisibility(View.VISIBLE);
                etPass.setVisibility(View.VISIBLE);
                etNewPass.setVisibility(View.VISIBLE);
                etRepeatPass.setVisibility(View.VISIBLE);
            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNewPass.getText().toString().equals(etRepeatPass.getText().toString()))
                    Toast.makeText(getActivity(), "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();

                else
                    updatePass();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        /*void onFragmentInteraction(Uri uri);*/
    }

    public void logout(){
        UtilUser.clearSharedPreferences(getActivity());
    }
    public void navegarSessionActivity(){
        startActivity(new Intent(getActivity(), SessionActivity.class));
    }

    public void updatePass() {



        String credentials = Credentials.basic(UtilUser.getEmail(getActivity()), etPass.getText().toString());
        String idUser = UtilUser.getId(getActivity());
        PassDto newPass = new PassDto(etNewPass.getText().toString());
        AuthService service = ServiceGenerator.createService(AuthService.class);
        Call<User> call = service.updatePass(credentials, idUser, newPass);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 401){
                    Toast.makeText(getActivity(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
                if (response.code() != 200) {
                    // error
                    Log.e("RequestError", response.message());

                } else {
                    logout();
                    navegarSessionActivity();
                    Toast.makeText(getActivity(), "Contraseña cambiada", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
