import { success, notFound, authorOrAdmin } from '../../services/response/'
import { Anuncio } from '.'
import { User } from '../user'
import { Comentario } from '../comentario'

// export const create = ({ user, bodymen: { body } }, res, next) =>
//     Anuncio.create({...body, ownerId: user })
//     .then((anuncio) => anuncio.view(true))
//     .then(success(res, 201))
//     .catch(next)

var id;

export const create = async({ user, bodymen: { body } }, res, next) => {
    await Anuncio.create({...body, ownerId: user })
        .then((anuncio) => {
            id = anuncio.view(true).id;
            return anuncio.view(true);
        })
        .then(success(res, 201))
        .catch(next)

    await User.findByIdAndUpdate(user.id, { $addToSet: { anuncios: id } }, { new: true })
        .then(success(res, 200))
        .catch(next)
}

export const index = ({ params, querymen: { query, select, cursor } }, res, next) => {
    Anuncio
        .find(query, select, cursor)
        .populate('ownerId', 'name picture role email')
        .then((result) => ({
            count: result.length,
            rows: result
        }))
        .then(success(res))
        .catch(next)
}

export const authenticatedIndex = ({ user, querymen: { query, select, cursor } }, res, next) => {
    Anuncio
        .find(query, select, cursor)
        .populate('ownerId', 'name picture role email')
        .then((result) => result.map((anuncio) => {
            let favoriteAnuncio = JSON.parse(JSON.stringify(anuncio))
            console.log(user.favs);
            console.log('Id ' + anuncio.id)
            favoriteAnuncio['isFav'] = user.favs.indexOf(anuncio.id) > -1
            return favoriteAnuncio
        }))
        .then((result) => ({
            count: result.length,
            rows: result
        }))
        .then(success(res))
        .catch(next)
}

export const indexMyAnuncios = ({ user, querymen: { query, select, cursor } }, res, next) => {
    Anuncio.find({ ownerId: user.id })
        .populate('ownerId', 'name picture role email')
        .then(notFound(res))
        .then((result) => result.map((anuncio) => {
            let favoriteAnuncio = JSON.parse(JSON.stringify(anuncio))
            console.log(user.favs);
            console.log('Id ' + anuncio.id)
            favoriteAnuncio['isFav'] = user.favs.indexOf(anuncio.id) > -1
            return favoriteAnuncio
        }))
        .then((result) => ({
            count: result.length,
            rows: result
        }))
        .then(success(res, 200))
        .catch(next)
}

export const show = ({ params }, res, next) =>
    Anuncio.findById(params.id)
    .then(notFound(res))
    .then((anuncio) => anuncio ? anuncio.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
    Anuncio.findById(params.id)
    .then(notFound(res))
    .then((anuncio) => anuncio ? Object.assign(anuncio, body).save() : null)
    .then((anuncio) => anuncio ? anuncio.view(true) : null)
    .then(success(res))
    .catch(next)

var idUser;
var comentarios;
export const destroy = async({ user, params }, res, next) => {
    await Anuncio.findById(params.id)
        .then(notFound(res))
        .then(authorOrAdmin(res, user, 'ownerId'))
        .then((anuncio) => {
            idUser = anuncio.view(true).ownerId;
            comentarios = anuncio.comentarios;
            anuncio ? anuncio.remove() : null;
        })
        .then(success(res, 204))
        .catch(next)

    await User.findByIdAndUpdate(idUser, { $pull: { anuncios: params.id } }, { new: true })
        .then(success(res, 200))
        .catch(next)

    /**
     * ESTO NO LO HACE
     */
    for (var i = 0; i < comentarios.lenth; i++) {
        await Comentario.findById(comentarios[i])
            .then(notFound(res))
            .then((comentario) => comentario ? comentario.remove() : null)
            .then(success(res, 204))
            .catch(next)
    }
}

export const addFavorite = ({ user, params }, res, next) =>
    User.findByIdAndUpdate(user.id, { $addToSet: { favs: params.id } }, { new: true })
    .then(success(res, 200))
    .catch(next)

export const delFavorite = ({ user, params }, res, next) =>
    User.findByIdAndUpdate(user.id, { $pull: { favs: params.id } }, { new: true })
    .then(success(res, 200))
    .catch(next)


export const userFavorites = ({ user, querymen: { query, select, cursor } }, res, next) => {
    query['_id'] = { $in: user.favs }
    Anuncio
        .find(query, select, cursor)
        .populate('ownerId', 'name picture email role')
        .then((result) => ({
            count: result.length,
            rows: result
        }))
        .then(success(res))
        .catch(next)
}