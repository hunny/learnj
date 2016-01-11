package hh.learnj.httpclient.usecase.download;

import java.io.File;
import java.net.URL;
import com.github.axet.vget.VGet;

public class DirectDownload {

    public static void main(String[] args) {
        try {
            // ex: http://www.youtube.com/watch?v=Nj6PFaDmp6c
            String url = "https://www.youtube.com/watch?v=pSHtufRNuKs";//args[0];
            // ex: "/Users/axet/Downloads"
            String path = "D:/BaiduYunDownload/";//args[1];
            VGet v = new VGet(new URL(url), new File(path));
            v.download();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
