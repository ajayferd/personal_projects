class Car:

    wheels = 4 # class variable

    def __init__(self, make, model, year, color):
        self.make = make
        self.model = model # instance variable
        self.year = year
        self.color = color

    def drive(self):
        print("This "+self.model+" is driving")
        return self
    
    def stop(self):
        print("This "+self.model+" has stopped")
        return self