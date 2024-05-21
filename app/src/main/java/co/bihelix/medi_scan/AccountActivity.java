package co.bihelix.medi_scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // If user is already signed in navigate to the home page
            Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Find views
//        TextView haveAccountTextView = findViewById(R.id.Words10);
        EditText userNameEditText = findViewById(R.id.mobilehint);
        EditText emailEditText = findViewById(R.id.mailhint);
        EditText passwordEditText = findViewById(R.id.addresshint);
        EditText retypePasswordEditText = findViewById(R.id.namehint);
        Button signupButton = findViewById(R.id.loginbutton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String username = userNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String retypePassword = retypePasswordEditText.getText().toString();

                // Check if any field is empty
                if (email.isEmpty() || password.isEmpty() || username.isEmpty() || retypePassword.isEmpty()) {
                    Toast.makeText(AccountActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if passwords match
                if (password.equals(retypePassword)) {
                    // Create user with email and password

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign up successful then navigate to home
                                        Toast.makeText(AccountActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                        saveUser(username, email, password, retypePassword);
                                    } else {
                                        // Sign up failed
                                        Toast.makeText(AccountActivity.this, "Sign up failed! Try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Passwords do not match msg
                    Toast.makeText(AccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*haveAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

            public void backTo(View view) {
                // Navigate to login page
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            public void goToHomePage(View view) {
                // Navigate to login page
                Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
                startActivity(intent);


            }
        });*/
    }

    public void saveUser(String userName, String email, String pass, String confPass) {
        try {
            Map<String, Object> city = new HashMap<>();
            city.put("name", userName);
            city.put("email", email);
            city.put("pass", pass);
            city.put("conf_pass", confPass);


            db.collection("user").document()
                    .set(city)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AccountActivity.this, "Sign up success !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AccountActivity.this, "Sign up failed! Try again later", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}