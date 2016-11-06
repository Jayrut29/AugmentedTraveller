package com.example.agt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bean.User;
import com.example.dao.UserDao;

public class LoginActivity extends Activity
{

	protected int RESULT_CODE_LOGGED_IN = 200;
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";

	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";

	private static String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View view)
			{
				/**
				 * String email = inputEmail.getText().toString(); String
				 * password = inputPassword.getText().toString(); UserFunctions
				 * userFunction = new UserFunctions(); Log.d("Button", "Login");
				 * JSONObject json = userFunction.loginUser(email, password);
				 * 
				 * // check for login response try { if
				 * (json.getString(KEY_SUCCESS) != null) {
				 * loginErrorMsg.setText(""); String res =
				 * json.getString(KEY_SUCCESS); if (Integer.parseInt(res) == 1)
				 * { // user successfully logged in // Store user details in
				 * SQLite Database DatabaseHandler db = new
				 * DatabaseHandler(getApplicationContext()); JSONObject
				 * json_user = json.getJSONObject("user");
				 * 
				 * // Clear all previous data in database
				 * userFunction.logoutUser(getApplicationContext());
				 * db.addUser(json_user.getString(KEY_NAME),
				 * json_user.getString(KEY_EMAIL), json.getString(KEY_UID),
				 * json_user.getString(KEY_CREATED_AT));
				 * 
				 * // Launch Dashboard Screen Intent dashboard = new
				 * Intent(getApplicationContext(), DashboardActivity.class);
				 * 
				 * // Close all views before launching Dashboard
				 * dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 * startActivity(dashboard);
				 * 
				 * // Close Login Screen finish(); } else { // Error in login
				 * loginErrorMsg.setText("Incorrect username/password"); } } }
				 * catch (JSONException e) { e.printStackTrace(); }
				 */

				if (checkAllFields())
				{
					User user = new User();
					user.setEmail(inputEmail.getText().toString().trim());
					user.setPassword(inputPassword.getText().toString().trim());
					UserDao dao = new UserDao(LoginActivity.this);
					if (dao.isValidUser(user))
					{
						Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
						SharedPreferences loginPref = LoginActivity.this.getSharedPreferences(getString(R.string.login_prefs), MODE_PRIVATE);
						Editor editor = loginPref.edit();
						editor.putString(getString(R.string.key_is_signed_in), "true");
						editor.putString(getString(R.string.key_my_name), getNameFromDB(user.getEmail()));
						editor.putString(getString(R.string.key_my_email), user.getEmail());
						editor.commit();
						setResult(RESULT_CODE_LOGGED_IN);
						finish();
					}
					else
					{
						Toast.makeText(LoginActivity.this, "invalid email/password", Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View view)
			{
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	protected String getNameFromDB(String string)
	{
		UserDao dao = new UserDao(LoginActivity.this);
		String name = dao.getNameFromEmail(string);
		return name;
	}

	protected boolean checkAllFields()
	{
		if (inputEmail.getText().toString().trim().equals(""))
		{
			inputEmail.setError("Please enter your email");
			inputEmail.requestFocus();
			return false;
		}
		else if (inputPassword.getText().toString().trim().equals(""))
		{
			inputPassword.setError("Please enter your password");
			inputPassword.requestFocus();
			return false;
		}
		return true;
	}
}
