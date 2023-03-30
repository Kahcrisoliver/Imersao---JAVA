import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;


import javax.imageio.ImageIO;

public class StickerGenerator {


    public void cria(InputStream inputStream, String sticker, String textoSticker) throws Exception {

        // leitura da imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // cria nova imagem em memória com transparência e com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original pra nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0 , 0, null);
        
        // configurar a fonte
        Font fonte = new Font(Font.SANS_SERIF, Font.BOLD, 80);
        Color corTexto = Color.YELLOW;
        Color corContorno = Color.black;
        float larguraContorno = 3f;
        
        // criar um stroke com o contorno
        Stroke stroke = new BasicStroke(larguraContorno, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        graphics.setStroke(stroke);

        // escrever uma frase na nova imagem centralizada horizontalmente
        graphics.setFont(fonte);
        int x = largura / 2 - graphics.getFontMetrics().stringWidth(textoSticker) / 2;
        int y = novaAltura - 100;

        // desenhar o contorno do texto
        graphics.setColor(corContorno);
        Shape textoContornado = stroke.createStrokedShape(fonte.createGlyphVector(graphics.getFontRenderContext(),textoSticker).getOutline());
        graphics.translate(x, y);
        graphics.draw(textoContornado);

        // escrever o texto sobre o contorno
        graphics.setColor(corTexto);
        graphics.translate(-larguraContorno / 2, -larguraContorno / 2);
        graphics.drawString(textoSticker, 0, 0);

        // escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(sticker));

    }

}
