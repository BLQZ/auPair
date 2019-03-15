export class UserDto {
    name: String;
    email: String;
    password: String;
    role: String;

    constructor(name: String, email:String, password: String, role: String) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}