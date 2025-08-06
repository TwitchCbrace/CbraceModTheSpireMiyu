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

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDynamicCard extends AbstractDefaultCard {

	private float renderedScale = 0.0F;

	// "How come DefaultCommonAttack extends CustomCard and not DynamicCard like all the rest?"

	// Well every card, at the end of the day, extends CustomCard.
	// Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend
	// it and
	// bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that
	// works).
	// Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
	// the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could
	// have easily
	// Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately.

	public AbstractDynamicCard(final String id, final String img, final int cost, final CardType type,
			final CardColor color, final CardRarity rarity, final CardTarget target) {

		super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type,
				color, rarity, target);
	}

	// 배경 이미지(경로는 임시로 아무곳에 저장함)
	private static final Texture RANGE_ICON = TextureLoader
			.getTexture(DefaultMod.getModID() + "Resources/images/1024/range_icon.png");
	private static final Texture COVER_ICON = TextureLoader
			.getTexture(DefaultMod.getModID() + "Resources/images/1024/cover_icon.png");
	// 거리를 표시할 때 쓸 폰트(카드 에너지 폰트)
	private static final BitmapFont FONT = FontHelper.cardEnergyFont_L;
	// 거리를 변경되었을때 쓸 색깔(초록색)
	private static final Color MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1F);
	// 기본 색
	private static final Color COLOR_WHITE = new Color(1F, 1F, 1F, 1F);

	// 대부분의 상황에서 랜더링됨,
	// 우클릭으로 카드를 확대했을 때는 랜더링 안됨
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		if (AbstractDungeon.player != null) { // 백과사전 안에서 랜더링되는 것을 방지함
			float currentDrawScale = drawScale * Settings.scale;
			// renderedScale 업데이트 로직
			if (Math.abs(currentDrawScale - renderedScale) > 0.001F) { // 일정 임계값 이상 변할 때만 업데이트
				renderedScale = currentDrawScale;
			}
			// Range Icon
			if (baseRangeMagicNumber > 0) {
				sb.setColor(Color.WHITE); // Explicitly set color to white
				sb.draw(RANGE_ICON, // 배경
						current_x - 166F, // 그려질 위치 지정(가로)
						current_y + 96F, // 그려질 위치 지정(세로)
						166F, -96F, // 아마 x,y 순임 뭐하는진 몰?루
						64F, 64F, // 이미지의 넓이 설정(크기에 맞춰 축소되거나 확대됨)
						currentDrawScale, currentDrawScale, // 아마도 ui관련한 무언가
						angle, // 각도
						0, 0, // 이미지 파일의 픽셀 시작점(불러오는 이미지의 일부분만 잘라서 쓸수도 있음, 순서는 x,y)
						64, 64, // 이미지 파일의 끝점(위와 같음)
						false, false // 좌우/상하 반전여부
				);
				// 표시색 결정, Range 값이 변했다면 초록색으로 표기됨
				Color rangeTempColor = COLOR_WHITE;
				if (isRangeMagicNumberModified)
					rangeTempColor = MODIFIED_COLOR;

				// 텍스트 표시
				FontHelper.renderRotatedText(sb, FONT, String.valueOf(baseRangeMagicNumber), current_x, current_y,
						-132F * renderedScale, 128F * renderedScale, angle, false, rangeTempColor);
			}

			// Cover Icon
			if (baseCoverMagicNumber > 0) {
				sb.setColor(Color.WHITE); // Explicitly set color to white
				sb.draw(COVER_ICON, // 배경
						current_x - 166F, // 그려질 위치 지정(가로)
						current_y + 32F, // 그려질 위치 지정(세로) - Range 아래에 위치
						166F, -32F, // y 오리진을 위치 오프셋에 맞게 조정
						64F, 64F, // 이미지의 넓이 설정(크기에 맞춰 축소되거나 확대됨)
						currentDrawScale, currentDrawScale, // 아마도 ui관련한 무언가
						angle, // 각도
						0, 0, // 이미지 파일의 픽셀 시작점(불러오는 이미지의 일부분만 잘라서 쓸수도 있음, 순서는 x,y)
						64, 64, // 이미지 파일의 끝점(위와 같음)
						false, false // 좌우/상하 반전여부
				);
				// 표시색 결정, Cover 값이 변했다면 초록색으로 표기됨
				Color coverTempColor = COLOR_WHITE;
				if (isCoverMagicNumberModified)
					coverTempColor = MODIFIED_COLOR;

				// 텍스트 표시
				FontHelper.renderRotatedText(sb, FONT, String.valueOf(baseCoverMagicNumber), current_x, current_y,
						-132F * renderedScale, 64F * renderedScale, angle, false, coverTempColor); // Range 텍스트 아래에 위치
			}
		}
	}

	// 백과사전에서 랜더링됨, 이미지만 나오게 했음
	@Override
	public void renderInLibrary(SpriteBatch sb) {
		super.renderInLibrary(sb);
		float libScale = drawScale * Settings.scale;
		if (baseRangeMagicNumber > 0) {
			sb.setColor(Color.WHITE); // Explicitly set color to white
			sb.draw(RANGE_ICON, current_x - 165F, current_y + 96F, 165F, -96F, 64F, 64F, libScale, libScale, angle, 0,
					0, 64, 64, false, false);
		}
		if (baseCoverMagicNumber > 0) {
			sb.setColor(Color.WHITE); // Explicitly set color to white
			sb.draw(COVER_ICON, current_x - 165F, current_y + 32F, 165F, -32F, 64F, 64F, libScale, libScale, angle, 0,
					0, 64, 64, false, false);
		}
	}

	@Override
	public void triggerOnExhaust() {
		AbstractDungeon.player.hand.group.stream().filter((card) -> card instanceof AbstractDynamicCard)
				.forEach((card) -> ((AbstractDynamicCard) card).triggerWhenOtherCardExhausted());
		AbstractDungeon.player.discardPile.group.stream().filter((card) -> card instanceof AbstractDynamicCard)
				.forEach((card) -> ((AbstractDynamicCard) card).triggerWhenOtherCardExhausted());
		AbstractDungeon.player.drawPile.group.stream().filter((card) -> card instanceof AbstractDynamicCard)
				.forEach((card) -> ((AbstractDynamicCard) card).triggerWhenOtherCardExhausted());
		AbstractDungeon.player.exhaustPile.group.stream().filter((card) -> card instanceof AbstractDynamicCard)
				.forEach((card) -> ((AbstractDynamicCard) card).triggerWhenOtherCardExhausted());
		super.triggerOnExhaust();
	}

	public void triggerWhenOtherCardExhausted() {
	}
}