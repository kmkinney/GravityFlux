//Moving Platform
//For now only horizontally moving


public class MovingPlatform extends Platform
{
    private double endX;
    //private double endY; add for vertical
    private double startX;
    //private double startY; add for vertical

    public MovingPlatform(double start_x, double start_y, double w, double h, double end_x, double vx)
    {
        super(start_x, start_y, w, h);
        this.startX=start_x*GameObject.SCALE;
        //this.startY=start_y*GameObject.SCALE;
        this.endX = end_x*GameObject.SCALE;
        //this.endY = end_y*GameObject.SCALE;
        this.setVelX(vx*GameObject.SCALE);
        //this.setVelY(vy*GameObject.SCALE);
    }
    public boolean isMoving(){return true;}
    public void tick()
    {
        if(this.velX!=0){
            if(this.velX > 0 && this.x >= this.endX){
                //this.setAX(-SWITCH_A);
                this.setVelX(-1*this.velX);
            }
            else if(this.velX < 0 && this.x <= this.startX){
                //this.setAX(SWITCH_A);
                this.setVelX(-1*this.velX);
            }
        }
        //Vertical moving if I want to implement
        // else if(this.velY!=0){
        //     if(this.velY > 0 && this.y >= this.endY){
        //         this.setVelY(-1*this.velY);
        //         //this.setAY(-SWITCH_A);
        //     }
        //     else if(this.velY < 0 && this.y <= this.startY){
        //         this.setVelY(-1*this.velY);
        //         //this.setAY(SWITCH_A);
        //     }
        // }
        super.tick();
    }
    public void scroll(double tx)
	{
        x+=tx;
        this.startX+=tx;
        this.endX+=tx;
	}
}
