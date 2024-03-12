package com.example.furnishings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furnishings.adaptors.ItemRecyclerViewAdaptor;

import com.example.furnishings.operations.OnItemClickListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ItemRecyclerViewAdaptor adapter;
    private RecyclerView view;
    private SearchView search;
    private TextView searchHint;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference tableRef = db.collection("Furniture/ProductWithSize/Category/Table/TableProducts");
    private CollectionReference chairRef = db.collection("Furniture/ProductWithSize/Category/Chair/ChairProducts");
    private CollectionReference bedRef = db.collection("Furniture/ProductWithSize/Category/Bed/BedProducts");
    private CollectionReference LightingRef = db.collection("Furniture/ProductWithoutSize/Category/Lighting/LightingProducts");
    private CollectionReference sofaRef = db.collection("Furniture/ProductWithoutSize/Category/Sofa/SofaProducts");

    //Store all items in the database
    private String[] Allnames;
    private String[] Allprices;
    private String[] AllshortDesc;
    private String[] Allcategories;

    private Boolean hasResult=false;

    /**
     * OnCreate method describe the behaviour of first time creating this search activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle bundle = this.getIntent().getExtras();
        //The user searching text passed here
        String text = bundle.getString("text");
        searchHint = (TextView) findViewById(R.id.search_hint);

        //lookup the search bar at top
        search = (SearchView) findViewById(R.id.searchview);
        search.setQuery(text, false);

        // Lookup the recyclerview in activity layout
        view = (RecyclerView) findViewById(R.id.search_items);

        //Get all products in the databse when first load in this search activity
        List<String> Lnames = new ArrayList<String>();
        List<String> Lprices = new ArrayList<String>();
        List<String> LshortDesc = new ArrayList<String>();
        List<String> Lcategories = new ArrayList<String>();
        Task task1 = tableRef.get();
        Task task2 = sofaRef.get();
        Task task3 = chairRef.get();
        Task task4 = bedRef.get();
        Task task5 = LightingRef.get();
        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1, task2, task3, task4, task5);
        allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {

                for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String name = (String) documentSnapshot.get("name");
                            Lnames.add(name);
                            Double price = (Double) documentSnapshot.get("price");
                            Lprices.add("$" + Double.toString(price));
                            String shortDesc = (String) documentSnapshot.get("brand")+ ", " + (String) documentSnapshot.get("material");
                            LshortDesc.add(shortDesc);
                            String category = (String) documentSnapshot.get("category");
                            Lcategories.add(category);
                    }
                }

                Allnames = new String[Lnames.size()];
                Allprices = new String[Lnames.size()];
                AllshortDesc = new String[Lnames.size()];
                Allcategories = new String[Lnames.size()];

                //Matched object lists to be displayed
                List<String> Mnames = new ArrayList<String>();
                List<String> Mprices = new ArrayList<String>();
                List<String> MshortDesc = new ArrayList<String>();
                List<String> Mcategories = new ArrayList<String>();

                for (int j = 0; j < Lnames.size(); j++) {
                    Allnames[j] = Lnames.get(j);
                    Allprices[j] = Lprices.get(j);
                    AllshortDesc[j] = LshortDesc.get(j);
                    Allcategories[j]=Lcategories.get(j);

                    //if this product has name matches to the search pattern with user input, add it
                    if(Lnames.get(j).toLowerCase().contains(text.toLowerCase())){
                        hasResult = true;
                        Mnames.add(Lnames.get(j));
                        Mprices.add(Lprices.get(j));
                        MshortDesc.add(LshortDesc.get(j));
                        Mcategories.add(Lcategories.get(j));
                    }
                }
                //Deal with polymorphism conditions here to display different message
                if(!hasResult){
                    searchHint.setText("No result for \""+ text+"\":");
                }else{
                    searchHint.setText("Search results for \""+ text+"\":");
                }

                //Turn matched objects into array
                String[] MAnames = new String[Mnames.size()];
                String[] MAprices = new String[Mprices.size()];
                String[] MAshortDesc = new String[MshortDesc.size()];
                String[] MAcategories = new String[Mcategories.size()];

                for (int j = 0; j < Mnames.size(); j++) {
                    MAnames[j] = Mnames.get(j);
                    MAprices[j] = Mprices.get(j);
                    MAshortDesc[j] = MshortDesc.get(j);
                    MAcategories[j] = Mcategories.get(j);
                }

                adapter = new ItemRecyclerViewAdaptor(MAnames, MAprices, MAshortDesc, MAcategories);
                view.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("-----------------------------------------------"+position);
                        Intent intent=new Intent(SearchActivity.this, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", MAnames[position]);
                        bundle.putString("category", MAcategories[position]);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                });
            }
        });

        //handle behavior of search bar, if the users wants to search again in this page
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String nestText = search.getQuery().toString();
                search.setQuery(nestText, false);

                List<String> Mnames = new ArrayList<String>();
                List<String> Mprices = new ArrayList<String>();
                List<String> MshortDesc = new ArrayList<String>();
                List<String> Mcategories = new ArrayList<String>();

                hasResult = false;

                for (int j = 0; j < Allnames.length; j++) {
                    if(Allnames[j].toLowerCase().contains(nestText.toLowerCase())){
                        hasResult = true;
                        Mnames.add(Allnames[j]);
                        Mprices.add(Allprices[j]);
                        MshortDesc.add(AllshortDesc[j]);
                        Mcategories.add(Allcategories[j]);
                    }
                }

                if(!hasResult){
                    searchHint.setText("No result for \""+ nestText+"\":");
                }else{
                    searchHint.setText("Search results for \""+ nestText+"\":");
                }

                String[] Mdnames = new String[Mnames.size()];
                String[] Mdprices = new String[Mprices.size()];
                String[] MdshortDesc = new String[MshortDesc.size()];
                String[] Mdcategories = new String[Mcategories.size()];

                for (int j = 0; j < Mnames.size(); j++) {
                    Mdnames[j] = Mnames.get(j);
                    Mdprices[j] = Mprices.get(j);
                    MdshortDesc[j] = MshortDesc.get(j);
                    Mdcategories[j] = Mcategories.get(j);
                }

                adapter = new ItemRecyclerViewAdaptor(Mdnames, Mdprices, MdshortDesc, Mdcategories);
                view.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("-----------------------------------------------"+position);
                        Intent intent=new Intent(SearchActivity.this, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", Mdnames[position]);
                        bundle.putString("category", Mdcategories[position]);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                });
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        // Create a LayoutManager
        LinearLayoutManager lm = new LinearLayoutManager(this);

        // Set layout manager to position the items
        view.setLayoutManager(lm);
    }
}
