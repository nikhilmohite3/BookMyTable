<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Restenex</title>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top" bgcolor="#838383"
            style="background-color: #838383;"><br> <br>
            <table width="600" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center" valign="top" bgcolor="#d3be6c"
                        style="background-color: #d3be6c; font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #000000; padding: 0px 15px 10px 15px;">

                        <div style="font-size: 48px; color:blue;">
                            <b>Your reservation is ${status}</b>
                        </div>

                        <div style="font-size: 24px; color: #555100;">
                            <br> Welcome to Restenex  <br>

                        <br> <br> <b> RestaurantId:- ${restaurantId}</b>
                        <br> <br> <b> RestaurantName:- ${restaurantName}</b>
                        <br> <br> <b> Booking Status:- ${status}</b>
                        <br>BookingId:- ${bookingId}<br>
                        <br> <br> <b>Date:- ${date}</b>
                        <br>NumberOfPerson:- ${numberOfPerson}<br>
                        <br>Seat Numbers:-<br>
                        <#list SeatNumbers as SeatNumber>
                          <p>${SeatNumber}
                        </#list>
                        <br> <br> <b>Address:- ${address}</b>
                        <br>





                    <br>


                        </div>
                    </td>
                </tr>
            </table> <br> <br></td>
    </tr>
</table>
</body>
</html>