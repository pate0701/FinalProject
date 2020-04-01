package com.example.finalproject;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;


public class GuardianDetailsActivityJava extends AppCompatActivity {

    ArticleSetGet articleSetGet;
    boolean isSaved;
    DataBaseHelperGuardian dataBaseHelperGuardian;
    Button saveArticleButton, viewInBrowserButton;
    CoordinatorLayout cLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        //setting up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbarId);
        toolbar.setTitle(R.string.title_detail);
        setSupportActionBar(toolbar);

        cLayout = findViewById(R.id.coordinatorLayout);

        articleSetGet = (ArticleSetGet) getIntent().getSerializableExtra("articleSetGet");
        String title = articleSetGet.getTitle();
        String section = articleSetGet.getSectionName();

        String url = articleSetGet.getUrl();
        final String id = articleSetGet.getId();

        TextView textView = findViewById(R.id.textView);
        textView.setText(title);
        TextView textView6 = findViewById(R.id.sectionDetails);
        textView6.setText(section);


        TextView textView10 = findViewById(R.id.textView10);
        textView10.setText(url);

        saveArticleButton = findViewById(R.id.saveArticle);

        dataBaseHelperGuardian = new DataBaseHelperGuardian(this);
        isSaved = dataBaseHelperGuardian.isSaved(id);

        String action;
        if (isSaved) {
            action = getString(R.string.action_delete);
        } else {
            action = getString(R.string.action_save);
        }

        viewInBrowserButton = findViewById(R.id.viewInBrowser);
        viewInBrowserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening URL in web browser
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(articleSetGet.getUrl()));
                startActivity(i);
            }
        });

        saveArticleButton.setText(action);
        saveArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSaved) {
                    showAlertDialog(id);
                } else {
                    dataBaseHelperGuardian.saveArticle(articleSetGet);
                    isSaved = dataBaseHelperGuardian.isSaved(id);
                    //display the Snackbar
                    Snackbar.make(cLayout, getString(R.string.article_saved), Snackbar.LENGTH_SHORT).show();
                    String action;
                    if (isSaved) {
                        action = getString(R.string.action_delete);
                    } else {
                        action = getString(R.string.action_save);
                    }
                    saveArticleButton.setText(action);
                }
            }
        });
    }

    private void showAlertDialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(getString(R.string.article_delete_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataBaseHelperGuardian.deleteArticle(id);
                isSaved = dataBaseHelperGuardian.isSaved(id);
                Snackbar.make(cLayout, getString(R.string.article_deleted), Snackbar.LENGTH_SHORT).show();
                String action;
                if (isSaved) {
                    action = getString(R.string.action_delete);
                } else {
                    action = getString(R.string.action_save);
                }
                saveArticleButton.setText(action);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.article_delete_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setTitle(getString(R.string.action_delete));
        builder.setMessage(getString(R.string.article_delete_message));
        builder.show();
    }
}