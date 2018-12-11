package a59070073.kmitl.ac.th.mobilefinal;

import android.content.ContentValues;
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
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class RegisterFragment extends Fragment {

    private SQLiteDatabase database;
    private ContentValues insert;

    @Nullable
    @Override

    public View onCreateView (@Nullable LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onActivityCreated (@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        saveBtn();
    }


    public void saveBtn(){
        Button save = getView().findViewById(R.id.register_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText registerUsername = getView().findViewById(R.id.register_username);
                EditText registerName = getView().findViewById(R.id.register_name);
                EditText registerAge = getView().findViewById(R.id.register_age);
                EditText registerPassword = getView().findViewById(R.id.register_password);

                String registerUsernameStr = registerUsername.getText().toString();
                String registerNameStr = registerName.getText().toString();
                String registerAgeStr = registerAge.getText().toString();
                int registerAgeInt = Integer.parseInt(registerAgeStr);
                String registerPasswordStr = registerPassword.getText().toString();
                int count = 0;

                if (registerUsernameStr.isEmpty() || registerNameStr.isEmpty() || registerAgeStr.isEmpty() || registerPasswordStr.isEmpty()){
                    Log.d("REGISTER", "REGISTER FAIL.");
                    Toast.makeText(getActivity(),"กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();
                }else{
                    if ((registerUsernameStr.length() >= 6) && (registerUsernameStr.length() <= 12)){
                        count += 1;
                        if (registerNameStr.contains(" ")){
                            count += 1;
                            if (registerAgeInt >= 10 && registerAgeInt <= 80){
                                count += 1;
                                if (registerPasswordStr.length() > 6){
                                    count += 1;
                                }else{
                                    count -= 1;
                                }
                            }else{
                                count -= 1;
                            }
                        }else{
                            count -= 1;
                        }
                    }else{
                        count -= 1;
                    }
                }

                if (count == 4){
                    insert.put("username", registerUsernameStr);
                    insert.put("name", registerNameStr);
                    insert.put("age", registerAgeStr);
                    insert.put("password", registerPasswordStr);


                    database = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);
                    database.insert("register", null, insert);
                    database.close();
                    insert.clear();

                    Toast.makeText(getActivity(), "Insert complete", Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER", "Insert data to DB");

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .addToBackStack(null)
                            .commit();

                }else{
                    Log.d("REGISTER", "REGISTER FAIL.");
                    Toast.makeText(getActivity(),"กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
