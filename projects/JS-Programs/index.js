 // JavaScript Code
let person = {
    firstName: 'Bob',
    lastName: 'Denver',
    age: 18
}
// Dot Notation (similar to C)
person.firstName = 'James';

let colorTheme = ['light','dark'];
colorTheme[2] = 'twilight';

function greet(person) {
    console.log('Howdy ' + person.firstName + ' ' + person.lastName);
}
console.log(person);
console.log(colorTheme);

greet(person);