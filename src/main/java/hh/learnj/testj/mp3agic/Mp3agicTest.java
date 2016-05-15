package hh.learnj.testj.mp3agic;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

/**
 * Reference URL: https://github.com/mpatric/mp3agic
 * @author hunnyhu
 *
 */
public class Mp3agicTest {

	public static void main(String[] args) throws Exception {

		String mp3dir = "/Users/hunnyhu/Music/音乐/";
		Mp3File mp3file = new Mp3File(mp3dir + "01.月光下的凤尾竹-葫芦丝.mp3");
		System.out.println("Length of this mp3 is: "
				+ mp3file.getLengthInSeconds() + " seconds");
		System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps "
				+ (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
		System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
		System.out.println("Has ID3v1 tag?: "
				+ (mp3file.hasId3v1Tag() ? "YES" : "NO"));
		System.out.println("Has ID3v2 tag?: "
				+ (mp3file.hasId3v2Tag() ? "YES" : "NO"));
		System.out.println("Has custom tag?: "
				+ (mp3file.hasCustomTag() ? "YES" : "NO"));
		if (mp3file.hasId3v1Tag()) {
			System.out.println("Id3v1tag====>");
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			System.out.println("Track: " + id3v1Tag.getTrack());
			System.out.println("Artist: " + id3v1Tag.getArtist());
			System.out.println("Title: " + id3v1Tag.getTitle());
			System.out.println("Album: " + id3v1Tag.getAlbum());
			System.out.println("Year: " + id3v1Tag.getYear());
			System.out.println("Genre: " + id3v1Tag.getGenre() + " ("
					+ id3v1Tag.getGenreDescription() + ")");
			System.out.println("Comment: " + id3v1Tag.getComment());
		}
		if (mp3file.hasId3v2Tag()) {
			System.out.println("Id3v2tag====>");
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			System.out.println("Track: " + id3v2Tag.getTrack());
			System.out.println("Artist: " + id3v2Tag.getArtist());
			System.out.println("Title: " + id3v2Tag.getTitle());
			System.out.println("Album: " + id3v2Tag.getAlbum());
			System.out.println("Year: " + id3v2Tag.getYear());
			System.out.println("Genre: " + id3v2Tag.getGenre() + " ("
					+ id3v2Tag.getGenreDescription() + ")");
			System.out.println("Comment: " + id3v2Tag.getComment());
			System.out.println("Composer: " + id3v2Tag.getComposer());
			System.out.println("Publisher: " + id3v2Tag.getPublisher());
			System.out.println("Original artist: "
					+ id3v2Tag.getOriginalArtist());
			System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
			System.out.println("Copyright: " + id3v2Tag.getCopyright());
			System.out.println("URL: " + id3v2Tag.getUrl());
			System.out.println("Encoder: " + id3v2Tag.getEncoder());
			byte[] albumImageData = id3v2Tag.getAlbumImage();
			if (albumImageData != null) {
				System.out.println("Have album image data, length: "
						+ albumImageData.length + " bytes");
				System.out.println("Album image mime type: "
						+ id3v2Tag.getAlbumImageMimeType());
			}
		}
		ID3v1 id3v1Tag;
		if (mp3file.hasId3v1Tag()) {
			id3v1Tag = mp3file.getId3v1Tag();
		} else {
			// mp3 does not have an ID3v1 tag, let's create one..
			id3v1Tag = new ID3v1Tag();
			mp3file.setId3v1Tag(id3v1Tag);
		}
		id3v1Tag.setTrack("5");
		id3v1Tag.setArtist("An Artist");
		id3v1Tag.setTitle("The Title");
		id3v1Tag.setAlbum("The Album");
		id3v1Tag.setYear("2001");
		id3v1Tag.setGenre(12);
		id3v1Tag.setComment("Some comment");
		// mp3file.save(mp3dir + "01.月光下的凤尾竹-葫芦丝-New.mp3");
		ID3v2 id3v2Tag;
		if (mp3file.hasId3v2Tag()) {
			id3v2Tag = mp3file.getId3v2Tag();
		} else {
			// mp3 does not have an ID3v2 tag, let's create one..
			id3v2Tag = new ID3v24Tag();
			mp3file.setId3v2Tag(id3v2Tag);
		}
		id3v2Tag.setTrack("5");
		id3v2Tag.setArtist("An Artist");
		id3v2Tag.setTitle("The Title");
		id3v2Tag.setAlbum("The Album");
		id3v2Tag.setYear("2001");
		id3v2Tag.setGenre(12);
		id3v2Tag.setComment("Some comment");
		id3v2Tag.setComposer("The Composer");
		id3v2Tag.setPublisher("A Publisher");
		id3v2Tag.setOriginalArtist("Another Artist");
		id3v2Tag.setAlbumArtist("An Artist");
		id3v2Tag.setCopyright("Copyright");
		id3v2Tag.setUrl("http://foobar");
		id3v2Tag.setEncoder("The Encoder");
		id3v2Tag.setAlbumImage(null, null);
		mp3file.save(mp3dir + "01.月光下的凤尾竹-葫芦丝-New.mp3");

	}

}
