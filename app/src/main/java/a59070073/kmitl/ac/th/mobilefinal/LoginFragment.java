package a59070073.kmitl.ac.th.mobilefinal;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private SQLiteDatabase database;
    private Cursor query;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonClick();
        registerBtn();
    }

    public void buttonClick(){
        Button loginBtn = getView().findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText loginUsername = getView().findViewById(R.id.login_username);
                EditText loginPassword = getView().findViewById(R.id.register_password);

                String loginUsernameStr = loginUsername.getText().toString();
                String loginPasswordStr = loginPassword.getText().toString();

                if (loginUsernameStr.isEmpty() || loginPasswordStr.isEmpty()){
                    Log.d("LOGIN", "login failed");
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }else{
                    database = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);
                    String db = String.format("SELECT username, password FROM register where password='%s'", loginPasswordStr);
                    query = database.rawQuery(db, null);

                    if (query.moveToNext()){
                        String username = query.getString(0);
                        String password = query.getString(1);
                        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", username).apply();
                        editor.commit();
                        Toast.makeText(getActivity(), "LOGIN PASS", Toast.LENGTH_SHORT).show();

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new HomeFragment())
                                .commit();

                    }else {
                        Toast.makeText(getActivity(), "LOGIN FAIL", Toast.LENGTH_SHORT).show();
                    }

                    query.close();
                    database.close();
                }
            }
        });
    }

    public void registerBtn(){
        TextView registerBtn = (TextView) getView().findViewById(R.id.login_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOGIN", "GOTO REGISTER");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
