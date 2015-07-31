public class RayCast
{
    /*
      Inputs
      - lines one is a two dimensional array that has equations of the lines that
        make up the polygon.
        It is in the form double[mx][c]
      - coords has coordinates that mark the end points of the lines in line1
        this is because lines will always intersect(even if it is at infinity) hence the coordinates limit acceptable
        points of intersections
      - lat: latitude
      - long: longitude
    */
    public boolean inpolygon(double[][] coords,double[][] lines, double lat, double longitude)
    {
        double[] pointLine = new double[2];
        double mx,c;

        //get value of mx using point in and fixed point -4,50(this is a point at sea so cant have geozones there)
        //1) Get gradient
        //Change ix x
        double xx = roundOff(-4-lat);
        if(xx==0)
            pointLine[0]=0;
        else
            pointLine[0] = roundOff((50-longitude)/xx);

        //Getting y intercept
        //y+(gradient*x) = c
        pointLine[1] = roundOff(50 + (-1*(pointLine[0]*(-4))));

        int intersects = intersections(lines,coords,pointLine);

        if(isEven(intersects))//Even means it cuts through the polygon and it is outside
        {
            return false;//Means the point it is outside the geozone
        }
        else
        {
            return true;//means it is inside the geozone
        }
    }
    
    
    
    
    //This determines whether the point is on a line (boundary of the polygon) or not
    /*
    Inputs
      - line one is an array that has equation of the line(on of the polygon's edges)
        It is in the form double[0]=mx double[1]=c
      - coords has coordinates that mark the end points of the lines in line1
        this is because lines will always intersect(even if it is at infinity) hence the coordinates limit acceptable
        points of intersections
      - lat :latitude of the point in question
      - long:longitude
    */
    public boolean onBoundary(double[] coords,double[] line,double lat, double longitude)
    {
        //Receives a coordinate, a line an the point in question
        double mx,c;

        //get value of mx
        //1) Get gradient
        //Change ix x
        double xx = coords[0]-lat;
        if(xx==0)
            mx=0;
        else
            mx = (coords[1]-longitude)/xx;

        //Getting y intercept
        //y+(gradient*x) = c
        c = coords[1] + (-1*(mx*(coords[0])));

        if(line[0]==mx || line[1]==c)
            return true;
        else
            return false;
    }
    
    
    //This calculates the number of times a line intersects a polygon
    /*
      Inputs
      - line1 one is a two dimensional array that has equations of the lines that
        make up the polygon.
        It is in the form double[mx][c]
      - coords has coordinates that mark the end points of the lines in line1
        this is because lines will always intersect(even if it is at infinity) hence the coordinates limit acceptable
        points of intersections
      - line two is the line from the point in question to some point that is obviously outside the polygon
        this is the line which intersects with the polygon
        
      Outputs
      - number of times the line2 intersects the polygon
    */
    public int intersections(double[][] line1,double[][] coords, double[] line2)
    {
        int intersectNumbers=0;

        //mx+c = mx+c
        for(int x=1;x<10;x++)
        {
            //X1+ -X2
            double partX = line1[x][0] + (-1*(line2[0]));
            //Y1+ -Y2
            double partY = line2[1] + (-1*(line1[x][1]));

            double intersectX,intersectY;
            //Y/X
            if(partX!=0)
            {
                intersectX = partY/partX;
                intersectY = intersectX*line2[0]+line2[1];

                //Checking whether these coordinates are within the lines (All lines intersect except parallel lines)
                //If gradient > 0 then the both coordinates of one point are greater than the other points'
                if(line1[x][0]>0)//positive gradient
                {
                    if((intersectX>coords[x][0] && intersectY>coords[x][1] && intersectX<coords[x-1][0] && intersectY<coords[x-1][1]) || (intersectX<coords[x][0] && intersectY<coords[x][1] && intersectX>coords[x-1][0] && intersectY>coords[x-1][1]))
                        intersectNumbers++;
                }
                else
                {
                    //Coordinates that are less than one
                    if((intersectX>coords[x][0] && intersectY<coords[x][1] && intersectX<coords[x-1][0] && intersectY>coords[x-1][1]) || (intersectX>coords[x][0] && intersectY<coords[x][1] && intersectX<coords[x-1][0] && intersectY>coords[x-1][1]))
                        intersectNumbers++;
                }
            }

        }

        return intersectNumbers;
    }
    
    
    //returns whether a number is even or not
    public boolean isEven(int number)
    {
        return number % 2 == 0;
    }


}
