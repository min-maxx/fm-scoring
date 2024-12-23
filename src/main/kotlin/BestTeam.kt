package fr.maxx4web


fun main() {

    // Formation racourci (4 lignes : D-MD-M-MO-AT) : ex 433 ou 532
    // ->Formation 4 lignes ( D-MD-M-MO-AT) : 433 -> 41221 ou 40303 ou ...
    //3 joueurs - 3 postes

    //1e joueur → meilleur poste
        //2e joueur → meilleur poste restant
            //3e joueur → poste restant
            //total compo
    //2e joueur → total compo 2
    //3e joueur → total compo 3
    //max compo
    //complexite : nb joueur * (nb poste + nb poste-1)

}


//DG / DD
//|- DL - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- LOf - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- ALS - Dé
//   |- Dé
//|- LOC - So / At
//   |- So
//   |- At
//|- LI - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- ALI - Dé
//   |- Dé
//DC
//|- DC - Dé / St / Co
//   |- Dé
//   |- St
//   |- Co
//|- Lib - Dé / So
//   |- Dé
//   |- So
//|- DéR - Dé / St / Co
//   |- Dé
//   |- St
//   |- Co
//|- DCS - Dé / St / Co
//   |- Dé
//   |- St
//   |- Co
//|- DCE - Dé / So / At
//   |- Dé
//   |- So
//   |- At

//ALG / ALD
//|- LOf - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- LOC - So / At
//   |- So
//   |- At
//|- LI - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//MD
//|- MD - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- MJR - Dé / So
//   |- Dé
//   |- So
//|- MRé - Dé / So
//   |- Dé
//   |- So
//|- MS - Dé
//   |- Dé
//|- 1/2 D - Dé
//   |- Dé
//|- RGA - So
//   |- So
//|- MJL - So
//   |- So
//|- VOL - So / At
//   |- So
//   |- At

//MG / MD
//|- ML - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- Ail - So / At
//   |- So
//   |- At
//|- AiD - Dé / So
//   |- Dé
//   |- So
//|- MjE - So / At
//   |- So
//   |- At
//|- AI - So / At
//   |- So
//   |- At

//MC
//|- MA - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- MJR - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- B2B - So
//   |- So
//|- MJA - So / At
//   |- So
//   |- At
//|- MRé - Dé / So
//   |- Dé
//   |- So
//|- MJL - So
//   |- So
//|- MEZ - So / At
//   |- So
//   |- At
//|- CAR - So
//   |- So


//MOG / MOD
//|- Ail - So / At
//   |- So
//   |- At
//|- MJA - So / At
//   |- So
//   |- At
//|- AtI - So / At
//   |- So
//   |- At
//|- AtS - At
//   |- At
//|- APE - So / At
//   |- So
//   |- At
//|- RMD - At
//   |- At
//|- AI - So / At
//   |- So
//   |- At

//MOC
//|- MO - So / At
//   |- So
//   |- At
//|- MJA - So / At
//   |- So
//   |- At
//|- AtS - At
//   |- At
//|- EG - So
//   |- So
//|- 9 1/2 - At
//   |- So
//   |- At

//BT
//|- AeR - So / At
//   |- So
//   |- At
//|- AtA - So / At
//   |- So
//   |- At
//|- AtP - So / At
//   |- So
//   |- At
//|- RS - At
//   |- At
//|- AtC - So / At
//   |- So
//   |- At
//|- AP - Dé / So / At
//   |- Dé
//   |- So
//   |- At
//|- AtS - At
//   |- At
//|- F9 - So
//   |- So
