package com.example.furnishings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;


import com.example.furnishings.operations.OnItemClickListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.example.furnishings.adaptors.CategoryCardViewAdaptor;
import com.example.furnishings.adaptors.PopularItemViewAdaptor;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PopularItemViewAdaptor popularItemViewAdaptor;
    private CategoryCardViewAdaptor categoryCardViewAdaptor;
    private SearchView searchBar;
    private RecyclerView itemView,categoryView;

    private static final String TAG = "MainActivity";


    //Firestore database code
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference tableRef = db.collection("Furniture/ProductWithSize/Category/Table/TableProducts");
    private CollectionReference chairRef = db.collection("Furniture/ProductWithSize/Category/Chair/ChairProducts");
    private CollectionReference bedRef = db.collection("Furniture/ProductWithSize/Category/Bed/BedProducts");
    private CollectionReference LightingRef = db.collection("Furniture/ProductWithoutSize/Category/Lighting/LightingProducts");
    private CollectionReference sofaRef = db.collection("Furniture/ProductWithoutSize/Category/Sofa/SofaProducts");

    private String[] categories={"Favourites", "Table", "Sofa", "Bed", "Chair", "Lighting"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Lookup the elements in MainActivity
        searchBar = (SearchView) findViewById(R.id.searchview);
        itemView = (RecyclerView) findViewById(R.id.popular_items);
        categoryView = (RecyclerView) findViewById(R.id.category_items);

        searchBar.setQueryHint("Look for modular sofa...");
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("Searching for: " + searchBar.getQuery().toString());
                Intent intent=new Intent(MainActivity.this, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("text", searchBar.getQuery().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //popular items recyclerview initialization here:
        List<String> Pnames = new ArrayList<String>();
        List<String> Pprices = new ArrayList<String>();
        List<String> Pcategories = new ArrayList<String>();
        //only retrieve items with popular "9" and "10"
        Task task1 = tableRef.whereGreaterThan("popular", "8").get();
        Task task2 = sofaRef.whereGreaterThan("popular", "8").get();
        Task task3 = chairRef.whereGreaterThan("popular", "8").get();
        Task task4 = bedRef.whereGreaterThan("popular", "8").get();
        Task task5 = LightingRef.whereGreaterThan("popular", "8").get();
        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1, task2, task3, task4, task5);
        allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String name = (String) documentSnapshot.get("name");
                        Pnames.add(name);
                        Double price = (Double) documentSnapshot.get("price");
                        Pprices.add("$" + Double.toString(price));
                        String category = (String) documentSnapshot.get("category");
                        Pcategories.add(category);
                    }
                }
                //Turn into array
                String[] PInames = new String[Pnames.size()];
                String[] PIprices = new String[Pnames.size()];
                String[] PIcategories = new String[Pnames.size()];

                for (int j = 0; j < Pnames.size(); j++) {
                    PInames[j] = Pnames.get(j);
                    PIprices[j] = Pprices.get(j);
                    PIcategories[j]=Pcategories.get(j);
                }
                popularItemViewAdaptor = new PopularItemViewAdaptor(PInames, PIprices, PIcategories);
                itemView.setAdapter(popularItemViewAdaptor);
                popularItemViewAdaptor.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("-----------------------------------------------"+position);
                        Intent intent=new Intent(MainActivity.this, DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", PInames[position]);
                        bundle.putString("category", PIcategories[position]);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                });
            }
        });

        //Category Cards initialization here
        categoryCardViewAdaptor = new CategoryCardViewAdaptor(categories);

        categoryView.setAdapter(categoryCardViewAdaptor);
        categoryCardViewAdaptor.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("-----------------------------------------------" + position);
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("category", categories[position]);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
        });

        // Fore a Horizontal RecyclerView use
         LinearLayoutManager lm1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // Set layout manager to position the items
        itemView.setLayoutManager(lm1);

        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        categoryView.setLayoutManager(lm2);
    }
}