package com.example.aupairapp.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aupairapp.Dialogs.DatePickerFragment;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private EditText etEmail, etNombre, etPassword, etPasswordRep, etCity,
            etProvince, etCountry, etAddress, etDate, etDescription, etHijos;
    /*private CircleImageView ivImagenPerfil;*/
    private ImageView ivImagenPerfil;
    private Button btnSubirImagen;
    Uri uriSelected;
    private Button btnRegistro, btnRegistroaLogin, btnNext, btnPrevious;
    private RadioGroup rgGenero, rgRole;
    private RadioButton rbAupair, rbFamily, rbMale, rbFemale;
    private boolean male;
    private FloatingActionButton btnA, btnB;
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
        rbAupair = view.findViewById(R.id.rbAuPair);
        rbFamily = view.findViewById(R.id.rbFamilia);
        rbFemale = view.findViewById(R.id.rbMujer);
        rbMale = view.findViewById(R.id.rbHombre);
        btnNext = view.findViewById(R.id.btnNext);
        rgGenero = view.findViewById(R.id.genero);
        rgRole = view.findViewById(R.id.role);

        etCity = view.findViewById(R.id.etCity);
        etProvince = view.findViewById(R.id.etProvince);
        etCountry = view.findViewById(R.id.etCountry);
        etDate = view.findViewById(R.id.etDate);
        etAddress = view.findViewById(R.id.etAddress);
        etDescription = view.findViewById(R.id.etDescription);
        btnA = view.findViewById(R.id.fb1);
        btnB = view.findViewById(R.id.fb2);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnRegistro = view.findViewById(R.id.btnSignup);
        etHijos = view.findViewById(R.id.etHijos);

        etCity.setVisibility(View.INVISIBLE);
        etProvince.setVisibility(View.INVISIBLE);
        etCountry.setVisibility(View.INVISIBLE);
        etDate.setVisibility(View.INVISIBLE);
        etAddress.setVisibility(View.INVISIBLE);
        etDescription.setVisibility(View.INVISIBLE);
        btnA.setEnabled(true);
        btnB.setEnabled(false);
        btnPrevious.setVisibility(View.INVISIBLE);
        btnRegistro.setVisibility(View.INVISIBLE);
        etHijos.setVisibility(View.INVISIBLE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setVisibility(View.INVISIBLE);
                etNombre.setVisibility(View.INVISIBLE);
                etPassword.setVisibility(View.INVISIBLE);
                etPasswordRep.setVisibility(View.INVISIBLE);
                btnRegistroaLogin.setVisibility(View.INVISIBLE);
                /*rbAupair.setVisibility(View.INVISIBLE);
                rbFamily.setVisibility(View.INVISIBLE);*/
                btnNext.setVisibility(View.INVISIBLE);
                rgGenero.setVisibility(View.INVISIBLE);
                rgRole.setVisibility(View.INVISIBLE);

                etCity.setVisibility(View.VISIBLE);
                etProvince.setVisibility(View.VISIBLE);
                etCountry.setVisibility(View.VISIBLE);
                etDate.setVisibility(View.VISIBLE);
                etAddress.setVisibility(View.VISIBLE);
                etDescription.setVisibility(View.VISIBLE);
                btnA.setEnabled(false);
                btnB.setEnabled(true);
                btnPrevious.setVisibility(View.VISIBLE);
                btnRegistro.setVisibility(View.VISIBLE);
                if(rbAupair.isChecked())
                    etHijos.setVisibility(View.INVISIBLE);
                else
                    etHijos.setVisibility(View.VISIBLE);
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setVisibility(View.VISIBLE);
                etNombre.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
                etPasswordRep.setVisibility(View.VISIBLE);
                btnRegistroaLogin.setVisibility(View.VISIBLE);
                /*rbAupair.setVisibility(View.INVISIBLE);
                rbFamily.setVisibility(View.INVISIBLE);*/
                btnNext.setVisibility(View.VISIBLE);
                rgGenero.setVisibility(View.VISIBLE);
                rgRole.setVisibility(View.VISIBLE);

                etCity.setVisibility(View.INVISIBLE);
                etProvince.setVisibility(View.INVISIBLE);
                etCountry.setVisibility(View.INVISIBLE);
                etDate.setVisibility(View.INVISIBLE);
                etAddress.setVisibility(View.INVISIBLE);
                etDescription.setVisibility(View.INVISIBLE);
                btnA.setEnabled(true);
                btnB.setEnabled(false);
                btnPrevious.setVisibility(View.INVISIBLE);
                btnRegistro.setVisibility(View.INVISIBLE);
                etHijos.setVisibility(View.INVISIBLE);
            }
        });




        /*etEmail.setVisibility(View.INVISIBLE);
        etNombre.setVisibility(View.INVISIBLE);
        etPassword.setVisibility(View.INVISIBLE);
        etPasswordRep.setVisibility(View.INVISIBLE);*/
        /*btnRegistro = view.findViewById(R.id.btnAddInmueble);*/
        /*ivImagenPerfil = view.findViewById(R.id.imageViewPreImg);*/

        /*btnSubirImagen = view.findViewById(R.id.buttonSubirImagen);*/

        /*btnSubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });*/

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        /*btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });*/

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

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etDate.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
    public static Date parseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd / MM / yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    public void register(){

        UserDto user;
        Date fecha = parseFecha(etDate.getText().toString());
        Log.d("FEcha:", fecha.toString());

        if(rbAupair.isChecked()){
            if(rbMale.isChecked()){
                male=true;
            } else {
                male=false;
            }
            user = new UserDto(etEmail.getText().toString(), etPassword.getText().toString(), etNombre.getText().toString(),
                    "http://www.paravivirenirlanda.com/wp-content/uploads/2017/02/Au-pair.jpg", "aupair",
                    etAddress.getText().toString(), etCity.getText().toString(), etProvince.getText().toString(), etCountry.getText().toString(),
                    male, 2, fecha);
        } else {
            if(rbMale.isChecked()){
                male=true;
            } else {
                male=false;
            }
            user = new UserDto(etEmail.getText().toString(), etPassword.getText().toString(), etNombre.getText().toString(),
                    "http://www.paravivirenirlanda.com/wp-content/uploads/2017/02/Au-pair.jpg", "family",
                    etAddress.getText().toString(), etCity.getText().toString(), etProvince.getText().toString(), etCountry.getText().toString(),
                    male, 2, fecha);
        }

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
