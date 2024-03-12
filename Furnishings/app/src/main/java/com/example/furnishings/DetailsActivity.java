package com.example.furnishings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.furnishings.adaptors.ViewPagerAdaptor;
import com.example.furnishings.firebaseOperations.ImageLoader;
import com.example.furnishings.firebaseOperations.ProductProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private ViewPagerAdaptor adapter;
    private List<ImageView> list = new ArrayList<>();
    private ImageView slide1, slide2, slide3;

    private String name;
    private String category;
    private boolean isfvt;
    private String id;

    private Button favouriteBtn, backBtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle bundle = this.getIntent().getExtras();

        name = bundle.getString("name");
        category = bundle.getString("category");

        TextView nameView=(TextView)findViewById(R.id.product_title);
        TextView priceView=(TextView)findViewById(R.id.product_price);
        TextView descView=(TextView)findViewById(R.id.product_desc);
        TextView specView=(TextView)findViewById(R.id.product_spec_info);

        favouriteBtn = (Button) findViewById(R.id.favBtn);
        favouriteBtn = (Button) findViewById(R.id.favBtn);
        backBtn = (Button) findViewById(R.id.back_btn);

        CollectionReference ref_1 = db.collection("Furniture/ProductWithSize/Category/"+category+"/"+category+"Products");
        CollectionReference ref_2 = db.collection("Furniture/ProductWithoutSize/Category/"+category+"/"+category+"Products");
        Task task1 = ref_1.whereEqualTo("name", name).get();
        Task task2 = ref_2.whereEqualTo("name", name).get();
        Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(task1, task2);
        allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        //three image slider setup
                        init();
                        addPoints();
                        setPagerListener();

                        String c = documentSnapshot.getString("category");
                        id = documentSnapshot.getId();
                        
                        ProductProvider provider = new ProductProvider(c, documentSnapshot);
                        provider.setProduct();

                        ImageLoader loader = new ImageLoader();
                        loader.loadImageView(slide1, provider.getImgLocation1());
                        loader.loadImageView(slide2, provider.getImgLocation2());
                        loader.loadImageView(slide3, provider.getImgLocation3());
                        nameView.setText(provider.getName());
                        priceView.setText("$"+Double.toString(provider.getPrice()));
                        descView.setText(provider.getDescription());
                        specView.setText(provider.getSpecification());
                        isfvt = provider.getFavourite();

                        if(isfvt){
                            favouriteBtn.setText("Already in Favourites!");
                        }
                    }
                }
            }
        });
    }

    public void fvtBtnClicked(View view){
        if(isfvt){
            isfvt = false;
            favouriteBtn.setText("Add to Favourites");
        }else{
            isfvt = true;
            favouriteBtn.setText("Already in Favourites!");
        }
        db.collection("Furniture/ProductWithSize/Category/"+category+"/"+category+"Products").document(id).update("favourite", isfvt);
        db.collection("Furniture/ProductWithoutSize/Category/"+category+"/"+category+"Products").document(id).update("favourite", isfvt);
        db.collection("Furniture/ProductWithSize/Category/"+category+"/"+category+"Products").document(id).update("favourite", isfvt);
        db.collection("Furniture/ProductWithoutSize/Category/"+category+"/"+category+"Products").document(id).update("favourite", isfvt);
    }

    public void backBtnClicked(View view){
        DetailsActivity.this.finish();
    }

    //set up listner
    private void setPagerListener() {
        switchPoint(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                switchPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //if image in position, change dot colour
    private void switchPoint(int position) {
        ImageView imageView;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            imageView = (ImageView) linearLayout.getChildAt(i);
            if (i == position) {
                imageView.setImageResource(R.drawable.point);
            } else {
                imageView.setImageResource(R.drawable.point_f);
            }
        }
    }

    private void addPoints() {
        for (int i = 0; i < list.size(); i++) {
            ImageView imgPoint = new ImageView(this);
            imgPoint.setImageResource(R.drawable.point_f);
            //设置布局属性
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 0, 0, 0);
            imgPoint.setLayoutParams(lp);
            linearLayout.addView(imgPoint);
        }
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.image_item);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_points);
        slide1 = new ImageView(this);
        slide1.setImageResource(R.drawable.loading);
        slide2 = new ImageView(this);
        slide2.setImageResource(R.drawable.loading);
        slide3 = new ImageView(this);
        slide3.setImageResource(R.drawable.loading);
        list.add(slide1);
        list.add(slide2);
        list.add(slide3);
        adapter=new ViewPagerAdaptor(list);
        viewPager.setAdapter(adapter);
    }
}