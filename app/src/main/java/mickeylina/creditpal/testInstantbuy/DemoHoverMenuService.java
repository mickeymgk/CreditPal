package mickeylina.creditpal.testInstantbuy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.ContextThemeWrapper;

import io.mattcarroll.hover.HoverMenu;
import io.mattcarroll.hover.HoverView;
import io.mattcarroll.hover.window.HoverMenuService;
import mickeylina.creditpal.R;

/**
 * Demo {@link HoverMenuService}.
 */
public class DemoHoverMenuService extends HoverMenuService  {

    private static final String TAG = "DemoHoverMenuService";
    private DemoHoverMenuTest mDemoHoverMenu;
    public static void showFloatingMenu(Context context) {
        context.startService(new Intent(context, DemoHoverMenuService.class));
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public  void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected Context getContextForHoverMenu() {
        return new ContextThemeWrapper(this, R.style.HoverTheme);
    }

    @Override
    protected void onHoverMenuLaunched(@NonNull Intent intent, @NonNull HoverView hoverView) {
        hoverView.setMenu(createHoverMenu());
        hoverView.collapse();
    }

    @NonNull
    private HoverMenu createHoverMenu() {
//        return new DemoHoverMenu(getApplicationContext(), "nonfullscreen");
//        return new DemoHoverMenuTest(getApplicationContext(),)

        mDemoHoverMenu = new DemoHoverMenuFactory().createDemoMenuFromCode(getContextForHoverMenu());

//            mDemoHoverMenuAdapter = new DemoHoverMenuFactory().createDemoMenuFromFile(getContextForHoverMenu());
        return mDemoHoverMenu;
    }

//    method to refresh the hover menu contents

    @SuppressLint("MissingPermission")
    public static void Refresh(Context context) {
        String pre= "*804%23";
        String dial = "tel:" + pre;
//        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)).FLAG_ACTIVITY_NEW_TASK);
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
