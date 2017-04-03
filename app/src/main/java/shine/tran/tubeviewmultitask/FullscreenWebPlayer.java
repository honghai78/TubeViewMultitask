package shine.tran.tubeviewmultitask;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class FullscreenWebPlayer extends Activity {

    static boolean active = false;
    static Activity fullScreenAct;

    ViewGroup parent;
    WebView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.active = true;
        fullScreenAct = this;
        super.onCreate(savedInstanceState);
        setContentView(shine.tran.tubeviewmultitask.R.layout.activity_fullscreen_web_player);

        LinearLayout ll = (LinearLayout) findViewById(shine.tran.tubeviewmultitask.R.id.layout_fullscreen);
        player = WebPlayer.getPlayer();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
        );

        parent = (ViewGroup) player.getParent();
        parent.removeView(player);

        ll.addView(player, params);

        WebPlayer.loadScript(JavaScript.playVideoScript());

    }
    @Override
    public void onBackPressed() {
        if(active && !getIntent().getBooleanExtra(Constants.SEND_BACK_PLAYER, false)){
            ((ViewGroup) player.getParent()).removeView(player);
            parent.addView(player);
            PlayerService.startAgain();
        }
        active = false;
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        if(active) {
            fullScreenAct.onBackPressed();
        }
        active = false;
        super.onPause();
    }
}
