package com.testv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView rvTrendingRepos;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.github)
    Button btnGithub;
    TimeTask timeTask;
    List<RepoModel> repoModelList = new ArrayList<>();
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //i have used a view injector
        ButterKnife.bind(this);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        timeTask = new TimeTask(getApplicationContext());
    }

    @OnClick(R.id.github)
    void displayTrendingRepos() {
        fetchCurrentTrendingRepos();
        //firebase analytics for button click event
        Bundle params = new Bundle();
        params.putString("github_button", "clicked");
        mFirebaseAnalytics.logEvent("on_click", params);
    }

    private void fetchCurrentTrendingRepos() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setUserAgent(System.getProperty("http.agent"));
        final RequestParams params = new RequestParams();
        params.put("q", "created:>" + timeTask.weekAgo());
        params.put("sort", "stars");
        params.put("order", "desc");
        client.get("https://api.github.com/search/repositories?", params, new AsyncHttpResponseHandler() {
            @Override

            public void onStart() {
                Log.d("github_api_params", String.valueOf(params));
                progressBar.setVisibility(View.VISIBLE);
                btnGithub.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                Log.d("github_api_response", s);
                progressBar.setVisibility(View.INVISIBLE);
                parseProductsResponseData(s);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.INVISIBLE);
                btnGithub.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), statusCode + ":" + error.getMessage(), LENGTH_LONG).show();

            }
        });
    }


    private void parseProductsResponseData(String s) {
        try {
            JSONObject dataObject = new JSONObject(s);
            JSONArray itemsArray = dataObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                RepoModel repoModel = new RepoModel();
                repoModel.setName(itemObject.getString("name"));
                repoModel.setFullName(itemObject.getString("full_name"));
                repoModel.setDescription(itemObject.getString("description"));
                repoModel.setUpdatedAt(timeTask.getTimeAgo(itemObject.getString("updated_at")));
//                repoModel.setUpdatedAt(itemObject.getString("updated_at"));
                repoModel.setStargazersCount(itemObject.getString("stargazers_count"));
                repoModel.setLanguage(itemObject.getString("language"));
                JSONObject ownerObject = itemObject.getJSONObject("owner");
                repoModel.setAvatarUrl(ownerObject.getString("avatar_url"));
                repoModelList.add(repoModel);
                setAdapterToRecyclerView();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setAdapterToRecyclerView() {
        LinearLayoutManager stylistLlm = new LinearLayoutManager(this);
        rvTrendingRepos.setHasFixedSize(true);
        rvTrendingRepos.setLayoutManager(stylistLlm);
        rvTrendingRepos.setItemViewCacheSize(repoModelList.size());
        CurrentReposAdapter currentReposAdapter = new CurrentReposAdapter(this, repoModelList);
        rvTrendingRepos.setAdapter(currentReposAdapter);
        rvTrendingRepos.setVisibility(View.VISIBLE);

    }
}
