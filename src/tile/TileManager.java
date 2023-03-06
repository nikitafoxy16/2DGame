package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	boolean drawPath = true;
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[50];
    	mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/worldmap.txt",0);
		loadMap("/maps/indoor01.txt",1);
		loadMap("/maps/dungeon01.txt",2);
		loadMap("/maps/dungeon02.txt",3);
	}
	
	public void getTileImage() {
		
		//PLACEHOLDER
		setup(0, "000", false);
		setup(1, "001", false);
		setup(2, "002", false);
		setup(3, "003", false);
		setup(4, "004", false);
		setup(5, "005", false);
		setup(6, "006", false);
		setup(7, "007", false);
		setup(8, "008", false);
		setup(9, "009", false);		
		setup(10, "010", false);
		setup(11, "011", false);
		setup(12, "012", false);
		setup(13, "013", false);
		setup(14, "014", false);
		setup(15, "015", false);
		setup(16, "016", true);
		setup(17, "017", false);
		setup(18, "018", true);
		setup(19, "019", true);
		setup(20, "020", true);
		setup(21, "021", true);
		setup(22, "022", true);
		setup(23, "023", true);
		setup(24, "024", true);
		setup(25, "025", true);
		setup(26, "026", true);
		setup(27, "027", true);
		setup(28, "028", false);
		setup(29, "029", false);
		setup(30, "030", false);
		setup(31, "031", false);
		setup(32, "032", true);
		setup(33, "033", false);
		setup(34, "034", false);
		setup(35, "035", false);
		setup(36, "036", false);
		setup(37, "037", false);
		setup(38,"trunk", false);
		
	}
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public void loadMap(String filePath,int map) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while (col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			
		}	
	}
	public void draw(Graphics2D g2) {
		
		int WorldCol = 0;
		int WorldRow = 0;
		
		while(WorldCol < gp.maxWorldCol && WorldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][WorldCol][WorldRow];
			
			int worldX = WorldCol * gp.tileSize;
			int worldY = WorldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
	          int screenY = worldY - gp.player.worldY + gp.player.screenY;
	          
	          if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
	             worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
	             worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
	             worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
	          }
            
			WorldCol++;
			
			if(WorldCol == gp.maxWorldCol) {
				WorldCol = 0;
				WorldRow++;
			}
		}	
		if(drawPath == true) {
			g2.setColor(new Color(255, 0, 0, 70));
			
			for(int i = 0; i < gp.pFinder.pathList.size(); i++) {
				
				int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
				int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
			}
		}
	}
}