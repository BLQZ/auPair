package com.example.aupairapp.Fragments;

import android.app.Activity;
import android.app.Service;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aupairapp.Generator.ServiceGenerator;
import com.example.aupairapp.Generator.UtilUser;
import com.example.aupairapp.MainActivity;
import com.example.aupairapp.Model.LoginResponse;
import com.example.aupairapp.Model.UserDto;
import com.example.aupairapp.R;
import com.example.aupairapp.Services.AuthService;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int READ_REQUEST_CODE = 42;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText etEmail;
    private EditText etNombre;
    private EditText etPassword;
    private EditText etPasswordRep;
    /*private CircleImageView ivImagenPerfil;*/
    private ImageView ivImagenPerfil;
    private Button btnSubirImagen;
    Uri uriSelected;
    private Button btnRegistro, btnRegistroaLogin;
    Context ctx;



    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        etEmail = view.findViewById(R.id.editTextEmailRegistro);
        etNombre = view.findViewById(R.id.editTextNombreRegistro);
        etPassword = view.findViewById(R.id.editTextPasswordRegistro);
        etPasswordRep = view.findViewById(R.id.editTextPasswordRepeat);
        btnRegistroaLogin = view.findViewById(R.id.buttonRegistroaLogin);
        btnRegistro = view.findViewById(R.id.btnAddInmueble);
        /*ivImagenPerfil = view.findViewById(R.id.imageViewPreImg);*/

        /*btnSubirImagen = view.findViewById(R.id.buttonSubirImagen);*/

        /*btnSubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });*/

        /*btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });*/

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btnRegistroaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.navegarLogin();
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
        void navegarLogin();
    }

    public void doRegister(){
        if (uriSelected != null) {

            AuthService service = ServiceGenerator.createService(AuthService.class);
            ctx=getView().getContext();

            try {
                InputStream inputStream = ctx.getContentResolver().openInputStream(uriSelected);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                int cantBytes;
                byte[] buffer = new byte[1024*4];

                while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                    baos.write(buffer,0,cantBytes);
                }


                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(ctx.getContentResolver().getType(uriSelected)), baos.toByteArray());


                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("picture", "picture", requestFile);


                RequestBody email = RequestBody.create(MultipartBody.FORM, etEmail.getText().toString().trim());
                RequestBody password = RequestBody.create(MultipartBody.FORM, etPassword.getText().toString().trim());
                RequestBody nombre = RequestBody.create(MultipartBody.FORM, etNombre.getText().toString().trim());

                if (validarString(email) && validarString(password) && validarString(nombre)) {

                    Call<LoginResponse> callRegister = service.doRegister(body, email, password, nombre);

                    callRegister.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                Log.d("Uploaded", "Éxito");
                                Log.d("Uploaded", response.body().toString());
                                UtilUser.setUserInfo(getActivity(), response.body().getUser());
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            } else {
                                Log.e("Upload error", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("Upload error", t.getMessage());
                        }
                    });

                }else {
                    Toast.makeText(getContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }



    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
                //showImage(uri);
                Glide
                        .with(this)
                        .load(uri)
                        .into(ivImagenPerfil);
                uriSelected = uri;
                btnRegistro.setEnabled(true);
            }
        }
    }

    Boolean validarString (RequestBody texto) {
        return texto != null && texto.toString().length() >0;
    }

    public void register(){
        UserDto user = new UserDto(etEmail.getText().toString(), etPassword.getText().toString(), etNombre.getText().toString());
        AuthService service = ServiceGenerator.createService(AuthService.class);
        Call<LoginResponse> call = service.register(user);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Log.d("Uploaded", "Éxito");
                    Log.d("Uploaded", response.body().toString());
                    UtilUser.setUserInfo(getActivity(), response.body().getUser());
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } else {
                    Log.e("Upload error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Upload error", t.getMessage());
            }
        });
    }
}
