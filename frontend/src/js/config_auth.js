import axios from "axios";
import bigInt from "big-integer";
import CryptoJS from "crypto-js";
import {ROOT_URL} from "@/main.js";
import router from "@/js/pages.js";

export function decrypt(encryptedMessage, key) {
    return CryptoJS.AES.decrypt(encryptedMessage, key, {mode: CryptoJS.mode.ECB}).toString(CryptoJS.enc.Utf8);
}

export function encrypt(message, key) {
    return CryptoJS.AES.encrypt(message, key, {mode: CryptoJS.mode.ECB}).toString();
}

export function decryptJsonList(data, decrypt_key) {
    return data.map(object => decryptJson(object, decrypt_key));
}

export function decryptJson(data, decrypt_key) {
    return Object.fromEntries(Object.entries(data).map(([key, value]) => [key, decrypt(value, decrypt_key)]));
}

function SHA256(value) {
    return CryptoJS.SHA256(value.toString());
}

export default class Config_auth {
    constructor() {
        this.a = bigInt.randBetween(20 ** 12, 20 ** 13);
        this.p = null;
        this.g = null;
        this.K = null;
    }

    async SessionId() {
        try {
            const response = await axios.post(
                `${ROOT_URL}/auth/dh`, {sessionId: localStorage.getItem('sessionId') === null ?
                        'null' : localStorage.getItem('sessionId')}
            );
            this.p = bigInt(response.data.p);
            this.g = bigInt(response.data.g);
            localStorage.setItem('sessionId', response.data.sessionId);
            await this.getB();
        } catch (error) {
            console.log(error);
        }
    }

    async getB() {
        try {
            const response = await axios.post(`${ROOT_URL}/auth/key`, {
                A: this.g.modPow(this.a, this.p).toString(),
                sessionId: localStorage.getItem('sessionId')
            });
            this.K = SHA256(bigInt(response.data.b).modPow(this.a, this.p));
        } catch (error) {
            console.log(error);
        }
    }

    async Check(current_path) {
        try {
            await axios.post(`${ROOT_URL}/auth/verification`, {
                token: encrypt(localStorage.getItem('token'), this.K),
                sessionId: localStorage.getItem('sessionId') === null ? 'null' : localStorage.getItem('sessionId'),

            });

            if (current_path === '/register' || current_path === '/login')
                await router.push('/');



        } catch (error) {

            if (error.response && error.response.status === 403)
                if (!(current_path === '/register' || current_path === '/'))
                    await router.push('/');
        }
    }

    async Check_role() {
        const response = await axios.post(`${ROOT_URL}/auth/getRole`, {
            token: encrypt(localStorage.getItem('token'), this.K),
            sessionId: localStorage.getItem('sessionId') === null ? 'null' : localStorage.getItem('sessionId'),
        });
        if (decrypt(response.data.role, this.K) !== "MANAGER")
            await router.push('/error');
    }
}