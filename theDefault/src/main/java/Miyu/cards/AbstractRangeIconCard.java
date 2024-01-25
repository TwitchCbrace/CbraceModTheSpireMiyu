package Miyu.cards;
import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

/*
* 거리 키워드를 가진 카드의 거리를 설명에 나타내지 않고 카드 에너지처럼 나타냄
*
* 카드의 에너지(비용) 밑에 이미지위에 숫자로 거리가 표시됩니다
* 거리를 나타내려면 이 클래스를 상속하면 됩니다
*/

public abstract class AbstractRangeIconCard extends AbstractDynamicCard {
	public AbstractRangeIconCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity,
			CardTarget target) {
		super(id, img, cost, type, color, rarity, target);
	}

	// 배경 이미지
	private static final Texture ICON = TextureLoader
			.getTexture(DefaultMod.getModID() + "Resources/images/1024/range_icon.png");
	// 거리를 표시할 때 쓸 폰트(무난하게 에너지 폰트 썼음)
	private static final BitmapFont FONT = FontHelper.cardEnergyFont_L;
	// 거리를 변경되었을때 쓸 색깔(초록색)
	private static final Color MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1F);
	// 기본 색
	private static final Color COLOR_WHITE = new Color(1F, 1F, 1F, 1F);

	// 대부분의 상황에서 랜더링됨,
	// 우클릭으로 카드를 확대 했을때나 백과사전에서는 랜더링 안됨
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		float scale = drawScale * Settings.scale;
		// 뒷배경 그리기
		sb.draw(ICON, // 배경
				current_x - 165F, // 그려질 위치 지정(가로축)
				current_y + 96F, // 그려질 위치 지정(세로축)
				165F, -96F, // 순서는 x,y 순임 뭐하는진 몰?루(x,y 에 더한 값의 반대부호임)
				64F, 64F, // 이미지의 넓이 설정(크기에 맞춰 축소되거나 확대됨)
				scale, scale, // 아마도 ui관련한 무언가
				angle, // 각도, 수정하면 어케되는지는 모름
				0, 0, // 이미지 파일의 픽셀 시작점(불러오는 이미지의 일부분만 잘라서 쓸수도 있음, 순서는 x,y)
				64, 64, // 이미지 파일의 끝점(위와 같음)
				false, false // 좌우/상하 반전여부
		);
		Color temp = COLOR_WHITE;
		// 표시색 결정
		if (isRangeMagicNumberModified)
			temp = MODIFIED_COLOR;

		// 텍스트 표시
		FontHelper.renderRotatedText(sb, FONT, String.valueOf(rangeMagicNumber), current_x, current_y, -132F * scale,
				127F * scale, angle, false, temp);
	}
}
