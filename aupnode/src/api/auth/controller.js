import { sign } from '../../services/jwt'
import { success, onlyAdmin } from '../../services/response/'
import { User } from '../user';

export const login = ({ user }, res, next) =>
    sign(user.id)
    .then((token) => ({ token, user: user.view(true) }))
    .then(success(res, 201))
    .catch(next)

export const loginAdmin = ({ user }, res, next) => {
    sign(user.id)
        .then(onlyAdmin(res, user))
        .then((token) => ({ token, user: user.view(true) }))
        .then(success(res, 201))
        .catch(next)
}