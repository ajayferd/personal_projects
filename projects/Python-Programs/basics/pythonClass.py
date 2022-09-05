from pythonCarClass import Car

car_1 = Car("BMW", "2002", 1978, "Silver")
car_2 = Car("Chevy", "Stingray", 1970, "Black")
print(car_1.make)
print(car_1.model)
print(car_1.year)
print(car_1.color)

print(car_2)

car_1.wheels = 2

print(car_1.wheels)
print(car_2.wheels)
print(Car.wheels)

# we can chain functions like this because the method returns self, without this feature the code will not compile
car_1.drive().stop()
car_2.drive()\
    .stop()