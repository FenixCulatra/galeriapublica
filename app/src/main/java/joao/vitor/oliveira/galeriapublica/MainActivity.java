package joao.vitor.oliveira.galeriapublica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    static int RESULT_REQUEST_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);

        bottomNavigationView = findViewById(R.id.btNav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                vm.setNavigationOpSelected(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.gridViewOp:
                        GridViewFragment gridViewFragment = GridViewFragment.newInstance();
                        setFragment(gridViewFragment);
                        break;

                    case R.id.listViewOp:
                        ListViewFragment listViewFragment = ListViewFragment.newInstance();
                        setFragment(listViewFragment);
                        break;
                }
                return true;
            }
        });
    }
    void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onResume(){
        super.onResume();
        List<String> permissions = new ArrayList<>();
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        checkForPermissions(permissions);
    }

    private void checkForPermissions(List<String> permissions){
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions){
            if (!hasPermission(permission)){
                permissionsNotGranted.add(permission);
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(permissionsNotGranted.size() > 0){
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), RESULT_REQUEST_PERMISSION);
            }else{
                MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
                int navigationOpSelected = vm.getNavigationOpSelected();
                bottomNavigationView.setSelectedItemId(navigationOpSelected);
            }
        }
    }

    private boolean hasPermission(String permission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            return ActivityCompat.checkSelfPermission(MainActivity.this,permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void OnRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION){

            for(String permission : permissions){
                if(!hasPermission(permission)){
                    permissionsRejected.add(permission);
                }
            }
        }

        if(permissionsRejected.size() > 0){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))){
                    new AlertDialog.Builder(MainActivity.this).setMessage("Para usar essa app é preciso conceder essas permissões").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                        }
                    }).create().show();
                }
            }
        }
        else{
            MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
            int navigationOpSelected = vm.getNavigationOpSelected();
            bottomNavigationView.setSelectedItemId(navigationOpSelected);
        }
    }
    // adicionando comentarios apenas por teste
}