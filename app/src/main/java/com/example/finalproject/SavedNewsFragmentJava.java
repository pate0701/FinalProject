
package com.example.finalproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.List;

public class SavedNewsFragmentJava extends Fragment {


    public SavedNewsFragmentJava() {

    }


    private List<ArticleSetGet> articleList;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved_news, container, false);
        listView = view.findViewById(R.id.listArticles);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GuardianDetailsActivityJava.class);
                intent.putExtra("articleSetGet", articleList.get(position));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DataBaseHelperGuardian dataBaseHelperGuardian = new DataBaseHelperGuardian(getActivity());

        articleList = dataBaseHelperGuardian.getAllSavedArticle();
        ListArticleAdapter listArticleAdapter = new ListArticleAdapter(getActivity(), articleList);
        listView.setAdapter(listArticleAdapter);
    }
}