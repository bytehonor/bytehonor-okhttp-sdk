package com.bytehonor.sdk.basic.lang.string;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utf8FilterTest {

	private static final Logger LOG = LoggerFactory.getLogger(Utf8FilterTest.class);

	private static final String TEXT = "🧠工作联系VX:z111x333 赠送一条：157cm 80斤ф";

	private static final String SPECIAL1 = "begin─ ━│┃╌╍╎╏┄ ┅┆┇┈ ┉┊┋┌┍┎┏┐┑┒┓└ ┕┖┗ ┘┙┚┛├┝┞┟┠┡┢┣ ┤┥┦┧┨┩┪┫┬ ┭ ┮ ┯ ┰ ┱ ┲ ┳ ┴ ┵ ┶ ┷ ┸ ┹ ┺ ┻┼ ┽ ┾ ┿ ╀ ╁ ╂ ╃ ╄ ╅ ╆ ╇ ╈ ╉ ╊ ╋ ╪ ╫ ╬═║╒╓╔ ╕╖╗╘╙╚ ╛╜╝╞╟╠ ╡╢╣╤ ╥ ╦ ╧ ╨ ╩ ╳╔ ╗╝╚ ╬ ═ ╓ ╩ ┠ ┨┯ ┷┏ ┓┗ ┛┳ ⊥ ﹃ ﹄┌ ╮ ╭ ╯╰end";

	private static final String SPECIAL2 = "begin。，、＇：∶；?‘’“”〝〞ˆˇ﹕︰﹔﹖﹑•¨….¸;！´？！～—ˉ｜‖＂〃｀@﹫¡¿﹏﹋﹌︴々﹟#﹩$﹠&﹪%*﹡﹢﹦﹤‐￣¯―﹨ˆ˜﹍﹎+=<＿_-\\ˇ~﹉﹊（）〈〉‹›﹛﹜『』〖〗［］《》〔〕{}「」【】︵︷︿︹︽_﹁﹃︻︶︸﹀︺︾ˉ﹂﹄︼❝❞end";

	private static final String SPECIAL3 = "begin°′″＄￥〒￠￡％＠℃℉﹩﹪‰﹫㎡㏕㎜㎝㎞㏎m³㎎㎏㏄º○¤%$º¹²³end";

	private static final String SPECIAL4 = "begin€£Ұ₴$₰¢₤¥₳₲₪₵元₣₱฿¤₡₮₭₩ރ円₢₥₫₦zł﷼₠₧₯₨Kčर₹ƒ₸￠end";

	private static final String SPECIAL5 = "begin↑↓←→↖↗↘↙↔↕➻➼➽➸➳➺➻➴➵➶➷➹▶►▷◁◀◄«»➩➪➫➬➭➮➯➱⏎➲➾➔➘➙➚➛➜➝➞➟➠➡➢➣➤➥➦➧➨↚↛↜↝↞↟↠↠↡↢↣↤↤↥↦↧↨⇄⇅⇆⇇⇈⇉⇊⇋⇌⇍⇎⇏⇐⇑⇒⇓⇔⇖⇗⇘⇙⇜↩↪↫↬↭↮↯↰↱↲↳↴↵↶↷↸↹☇☈↼↽↾↿⇀⇁⇂⇃⇞⇟⇠⇡⇢⇣⇤⇥⇦⇧⇨⇩⇪↺↻⇚⇛end";

	private static final String EMOJIS1 = "1🌹2🍀3🍎4💰5📱6🌙7🍁8🍂9🍃0🌷1💎2🔪3🔫4🏀5⚽6⚡8👄9👍0🔥";

	private static final String EMOJIS2 = "👦👧👨👩👴👵👶👱👮👲👳👷👸💂🎅👰👼💆💇🙍🙎🙅🙆💁🙋🙇🙌🙏👤👥🚶🏃👯💃👫👬👭💏💑👪";

	private static final String EMOJIS3 = "👣👀👂👃👅👄💋👓👔👕👖👗👘👙👚👛👜👝🎒💼👞👟👠👡👢👑👒🎩🎓💄💅💍🌂";

	private static final String EMOJIS4 = "🙈🙉🙊🐵🐒🐶🐕🐩🐺🐱😺😸😹😻😼😽🙀😿😾🐈🐯🐅🐆🐴🐎🐮🐂🐃🐄🐷🐖🐗🐽🐏🐑🐐🐪🐫🐘🐭🐁🐀🐹🐰🐇🐻🐨🐼🐾🐔🐓🐣🐤🐥🐦🐧🐸🐊🐢🐍🐲🐉🐳🐋🐬🐟🐠🐡🐙🐚🐌🐛🐜🐝🐞🦋";

	private static final String EMOJIS5 = "🍇🍈🍉🍊🍋🍌🍍🍎🍏🍐🍑🍒🍓🍅🍆🌽🍄🌰🍞🍖🍗🍔🍟🍕🍳🍲🍱🍘🍙🍚🍛🍜🍝🍠🍢🍣🍤🍥🍡🍦🍧🍨🍩🍪🎂🍰🍫🍬🍭🍮🍯🍼☕🍵🍶🍷🍸🍹🍺🍻🍴";

	@Test
	public void testFilterOffUtf8Mb4() {
		Map<String, String> texts = new HashMap<String, String>();
		texts.put("TEXT", TEXT);
		texts.put("EMOJIS1", EMOJIS1);
		texts.put("EMOJIS2", EMOJIS2);
		texts.put("EMOJIS3", EMOJIS3);
		texts.put("EMOJIS4", EMOJIS4);
		texts.put("EMOJIS5", EMOJIS5);

		texts.put("SPECIAL1", SPECIAL1);
		texts.put("SPECIAL2", SPECIAL2);
		texts.put("SPECIAL3", SPECIAL3);
		texts.put("SPECIAL4", SPECIAL4);
		texts.put("SPECIAL5", SPECIAL5);

		try {
			for (Entry<String, String> item : texts.entrySet()) {
				String s1 = StringRemoveUtils.removeUtf8Mb4(item.getValue());
				LOG.info("U type:{}, old:{}, new:{}, ({})", item.getKey(), item.getValue().length(), s1.length(), s1);
			}

			LOG.info("({}), {}", EMOJIS1, EMOJIS1.length());
		} catch (UnsupportedEncodingException e) {
			LOG.error("xxx", e);
		}
		int times = 1000;
		long start = System.nanoTime();
		for (int i = 0; i < times; i++) {
			try {
			    StringRemoveUtils.removeUtf8Mb4(EMOJIS1);
			} catch (UnsupportedEncodingException e) {
				LOG.error("asf", e);
			}
		}
		long cost1 = System.nanoTime() - start;
		LOG.info("cost1:{}", cost1);
		assertTrue("*testFilterOffUtf8Mb4*", true);
	}

}
