<!DOCTYPE html>
<html lang = "en">
    <?php
        
        if(array_key_exists('Add To Cart',$_POST)){
            echo "Item Added to Cart!";
        }
    ?>
    <head>
    <!-- heading with reference to style sheet and img-->
        <meta charset = "utf-8">
        <title>Jay's Footwear</title>
        <h1>Jay's Footwear</h1>
        <img src = shoelogo.jpg alt = "Jay's Logo" class="center">
        <h3>A Discount Footwear Superstore</h3>
        <h5>By Alejandro Fernandez</h5>
        <!-- embedded PHP to tell user what day is-->
        <h4><?php echo "Today is " . date("Y/m/d") . "<br>"; ?></h4>
        <link rel="stylesheet" href="style.css">
    </head>
    <nav>
        <a href = '#Clothes'>Clothes</a> &nbsp;
        <a href = "#Men's Shoes">Men's Shoes</a> &nbsp;
        <a href = "#Women's Shoes">Women's Shoes</a> &nbsp;
        <a href = "#Kid's Shoes">Kid's Shoes</a> &nbsp;
    </nav>
    
    <section id = "Clothes">
        <h2>Clothes</h2>
        <ul>
            <li><br><br>Grey Hoodie</li>
            <caption>Everyone will love you in this Grey Hoodie<br> Price: $14.99<br></caption>
            <img src =grey-hoodie.jpg  alt="Grey Hoodie" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
            <li><br><br>Fluffy Jacket</li>
            <caption>No one will be able to stop looking at you in this<br> Price: $19.99<br></caption>
            <img src =fluffy-jacket.jpg alt="Fluffy Jacket" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
            <li><br><br>Baggy Pants</li>
            <caption>Try not to trip in this<br>Price: $9.99<br></caption>
            <img src =baggy-pants.jpg alt="Baggy Pants" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
        </ul>

    </section>
    <section id ="Men's Shoes">
        <!-- Displays all mens shoes-->
        <h2>Men's Shoes</h2>
        <ul>
            <li><br><br>Air Max Scorpion</li>
            <caption>These Sesame Comfy shoes are sure to make an impression <br>Price: $250<br></caption>
            <img src ="scorpion.jpg" alt="Scorpion Shoe" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
            <li><br><br>Jordan 4 Retro</li>
            <caption>These Jordan 4's with a white and blue colorway make for an impressive shoe <br>Price: $210<br></caption>
            <img src ="jordan-4.jpg" alt="Jordan 4" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
            <li><br><br>Offline 2.0</li>
            <caption>These Velvet Brown slides are the perfect everyday shoe<br>Price: $130 <br></caption>
            <img src ="offline2.jpg" alt="Offline 2" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
        </ul>
    </section>
    <section id ="Women's Shoes">
        <!-- displays all womens shoes-->
        <h2>Women's Shoes</h2>
        <ul>
            <li><br><br>Women's Dunk High</li>
            <caption>These deep purple and white shoes are instant classics<br>Price: $125<br></caption>
            <img src ="dunkhigh.jpg" alt="Women's Dunk High" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
            <li><br><br>Air Max 95</li>
            <caption>These velvet teal shoes are very very nice!<br>Price: $190<br></caption>
            <img src ="airmax95.jpg" alt="Air Max 95" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
            <li><br><br>Converse X CLOT</li>
            <caption>These shoes are sure to grab attention with its unique look<br>Price: $130<br></caption>
            <img src ="clot.jpg" alt="Converse X CLOT" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
        </ul>
    </section>
    <section id = "Kid's Shoes">
        <!-- displays all kids shoes-->
        <h2>Kid's Shoes</h2>
        <ul>
            <li><br><br>Nike SB Dunk Low Toddler</li>
            <caption>Your kid will be the coolest kid on the block<br>Price: $75<br></caption>
            <img src ="sblowtoddler.jpg" alt="SB Low Toddler" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
            <li><br><br>Jordan 5</li>
            <caption>The Dark Concord colorwave is sure to be a hit with the teachers<br>Price: $75<br></caption>
            <img src ="darkconcord.jpg" alt="Jordan 5 Dark Concord" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
            <li><br><br>Jordan 11</li>
            <caption>These shoes will make your kid the best basketball player<br>Price: $75<br></caption>
            <img src ="jordan11.jpg" alt="Jordan 11" class="center">
            <form method="post">
                <input type = "submit" name = "Add To Cart"
                id = "Add To Cart" value="Add To Cart" class="button"/>
            <form>
        </ul>
    </section>
</html>