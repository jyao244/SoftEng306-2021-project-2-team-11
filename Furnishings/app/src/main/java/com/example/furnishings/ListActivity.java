package com.example.furnishings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.furnishings.adaptors.ItemRecyclerViewAdaptor;
import com.example.furnishings.firebaseOperations.ImageLoader;
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

public class ListActivity extends AppCompatActivity {
    private ItemRecyclerViewAdaptor adapter;
    private RecyclerView view;
    private TextView title, subTitle;

    private String category;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference tableRef = db.collection("Furniture/ProductWithSize/Category/Table/TableProducts");
    private CollectionReference chairRef = db.collection("Furniture/ProductWithSize/Category/Chair/ChairProducts");
    private CollectionReference bedRef = db.collection("Furniture/ProductWithSize/Category/Bed/BedProducts");
    private CollectionReference LightingRef = db.collection("Furniture/ProductWithoutSize/Category/Lighting/LightingProducts");
    private CollectionReference sofaRef = db.collection("Furniture/ProductWithoutSize/Category/Sofa/SofaProducts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle bundle = this.getIntent().getExtras();
        category = bundle.getString("category");

        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(category);

        subTitle = (TextView) findViewById(R.id.toolbar_subtitle);

        if(category.equals("Favourites")){
            subTitle.setText("\"The inspirational place to live\"");
        }else {
            CollectionReference ca_ref1 = db.collection("Furniture/ProductWithSize/Category");
            CollectionReference ca_ref2 = db.collection("Furniture/ProductWithoutSize/Category");
            Task ca_task1 = ca_ref1.whereEqualTo("Category", category).get();
            Task ca_task2 = ca_ref2.whereEqualTo("Category", category).get();
            Task<List<QuerySnapshot>> ca_Tasks = Tasks.whenAllSuccess(ca_task1, ca_task2);
            ca_Tasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
                @Override
                public void onSuccess(List<QuerySnapshot> querySnapshots) {
                    for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            subTitle.setText((String) documentSnapshot.get("SubTitle"));
                        }
                    }
                }
            });
        }

        ImageView image = (ImageView) findViewById(R.id.image_background);
        String imgLocation = ("Category Images/"+category+".jpg");
        ImageLoader loader = new ImageLoader();
        loader.loadImageView(image, imgLocation);

        // Lookup the recyclerview in activity layout
        view = (RecyclerView) findViewById(R.id.search_items);

        List<String> Pnames = new ArrayList<String>();
        List<String> Pprices = new ArrayList<String>();
        List<String> PshortDesc = new ArrayList<String>();
        List<String> Pcategories = new ArrayList<String>();

        //check if favourites category
        if(category.equals("Favourites")){
            Task task1 = tableRef.get();
            Task task2 = sofaRef.get();
            Task task3 = chairRef.get();
            Task task4 = bedRef.get();
            Task task5 = LightingRef.get();
            Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1,task2,task3,task4,task5);
            allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
                @Override
                public void onSuccess(List<QuerySnapshot> querySnapshots) {
                    for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            //if this item is in favourite list
                            if((Boolean) documentSnapshot.get("favourite")) {
                                String name = (String) documentSnapshot.get("name");
                                Pnames.add(name);
                                Double price = (Double) documentSnapshot.get("price");
                                Pprices.add("$" + Double.toString(price));
                                String shortDesc = (String) documentSnapshot.get("brand")+ ", " + (String) documentSnapshot.get("material");
                                PshortDesc.add(shortDesc);
                                String category = (String) documentSnapshot.get("category");
                                Pcategories.add(category);
                            }
                        }
                    }

                    String[] PInames = new String[Pnames.size()];
                    String[] PIprices = new String[Pnames.size()];
                    String[] PIshortDesc = new String[Pnames.size()];
                    String[] PIcategories = new String[Pnames.size()];

                    for (int j = 0; j < Pnames.size(); j++) {
                        PInames[j] = Pnames.get(j);
                        PIprices[j] = Pprices.get(j);
                        PIshortDesc[j] = PshortDesc.get(j);
                        PIcategories[j]=Pcategories.get(j);
                    }

                    // Create adapter passing in the sample user data
                    adapter = new ItemRecyclerViewAdaptor(PInames, PIprices, PIshortDesc, PIcategories);
                    // Attach the adapter to the recyclerview to populate items
                    view.setAdapter(adapter);

                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            System.out.println("-----------------------------------------------"+position);
                            Intent intent=new Intent(ListActivity.this, DetailsActivity.class);
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
        }
        //Not-favourites, normal category cases
        else {
            CollectionReference ref_1 = db.collection("Furniture/ProductWithSize/Category/" + category + "/" + category + "Products");
            CollectionReference ref_2 = db.collection("Furniture/ProductWithoutSize/Category/" + category + "/" + category + "Products");

            Task task1 = ref_1.get();
            Task task2 = ref_2.get();
            Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1, task2);
            allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
                @Override
                public void onSuccess(List<QuerySnapshot> querySnapshots) {
                    for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String name = (String) documentSnapshot.get("name");
                            Pnames.add(name);
                            Double price = (Double) documentSnapshot.get("price");
                            Pprices.add("$" + Double.toString(price));
                            String shortDesc = (String) documentSnapshot.get("brand") + ", " + (String) documentSnapshot.get("material");
                            PshortDesc.add(shortDesc);
                            String category = (String) documentSnapshot.get("category");
                            Pcategories.add(category);
                        }
                    }

                    String[] PInames = new String[Pnames.size()];
                    String[] PIprices = new String[Pnames.size()];
                    String[] PIshortDesc = new String[Pnames.size()];
                    String[] PIcategories = new String[Pnames.size()];

                    for (int j = 0; j < Pnames.size(); j++) {
                        PInames[j] = Pnames.get(j);
                        PIprices[j] = Pprices.get(j);
                        PIshortDesc[j] = PshortDesc.get(j);
                        PIcategories[j] = Pcategories.get(j);
                    }

                    adapter = new ItemRecyclerViewAdaptor(PInames, PIprices, PIshortDesc, PIcategories);
                    view.setAdapter(adapter);

                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            System.out.println("-----------------------------------------------" + position);
                            Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("name", PInames[position]);
                            bundle.putString("category", PIcategories[position]);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
            });
        }

        // Create a LayoutManager
        LinearLayoutManager lm = new LinearLayoutManager(this);

        // Set layout manager to position the items
        view.setLayoutManager(lm);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //Each time this activity is loaded call the method to update it
        handleListInFavourite();
        }

    /**
     * This method is used to update the favourite list in case user has change the favourites list
     * in item detail activity and come back to favourtes list
      */
    public void handleListInFavourite(){
        List<String> Pnames = new ArrayList<String>();
        List<String> Pprices = new ArrayList<String>();
        List<String> PshortDesc = new ArrayList<String>();
        List<String> Pcategories = new ArrayList<String>();

        //check if favourites category
        if(category.equals("Favourites")){
            Task task1 = tableRef.get();
            Task task2 = sofaRef.get();
            Task task3 = chairRef.get();
            Task task4 = bedRef.get();
            Task task5 = LightingRef.get();
            Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1,task2,task3,task4,task5);
            allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
                @Override
                public void onSuccess(List<QuerySnapshot> querySnapshots) {
                    for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            //if this item is in favourite list
                            if((Boolean) documentSnapshot.get("favourite")) {
                                String name = (String) documentSnapshot.get("name");
                                Pnames.add(name);
                                Double price = (Double) documentSnapshot.get("price");
                                Pprices.add("$" + Double.toString(price));
                                String shortDesc = (String) documentSnapshot.get("brand")+ ", " + (String) documentSnapshot.get("material");
                                PshortDesc.add(shortDesc);
                                String category = (String) documentSnapshot.get("category");
                                Pcategories.add(category);
                            }
                        }
                    }
                    String[] PInames = new String[Pnames.size()];
                    String[] PIprices = new String[Pnames.size()];
                    String[] PIshortDesc = new String[Pnames.size()];
                    String[] PIcategories = new String[Pnames.size()];

                    for (int j = 0; j < Pnames.size(); j++) {
                        PInames[j] = Pnames.get(j);
                        PIprices[j] = Pprices.get(j);
                        PIshortDesc[j] = PshortDesc.get(j);
                        PIcategories[j]=Pcategories.get(j);
                    }
                    adapter = new ItemRecyclerViewAdaptor(PInames, PIprices, PIshortDesc, PIcategories);
                    view.setAdapter(adapter);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            System.out.println("-----------------------------------------------"+position);
                            Intent intent=new Intent(ListActivity.this, DetailsActivity.class);
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
        }
    }
}