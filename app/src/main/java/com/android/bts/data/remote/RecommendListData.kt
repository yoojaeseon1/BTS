package com.android.bts.data.remote

import android.net.Uri


data class Recommend(
    val thumbnail: String,
    val subTitle: String,
    val title: String,
    val info: String,
    val searchWord: String
)


class RecommendList {
    fun getRecommendList() = initRecommendList()

    private fun initRecommendList(): MutableList<Recommend> {

        return mutableListOf(
            Recommend(
                "https://edelweiss22657.cafe24.com/bizdemo124692/img/sub01/sub01_01.jpg",

                "한국의 스위스",

                "에델바이스 스위스 테마파크",

                "#경기 #가평 #전통의상 #박물관 #포토존 \n #피쉬앤칩스 #치즈퐁듀 #에델바이스비어",

                "가평 스위스마을"
            ),
            Recommend(
                "https://cdn.gijn.kr/news/photo/202109/409477_312802_3028.jpg",

                "한국의 프랑스&이탈리아",

                "쁘띠프랑스 & 이탈리아 마을",

                "#경기 #가평 #오르골 #마리오네트 #가족여행\n #어린왕자 #피노키오 #레오나르도다빈치",

                "쁘띠프랑스 "
            ),
            Recommend(
                "https://metaprovence.co.kr/wp-content/uploads/kboard_attached/3/201906/5d084b253981b6551166.jpg",

                "한국의 프랑스",

                "메타 프로방스",

                "#전남 #담양 #메타세콰이어 #가로수길\n #야경 #공방 #카페 #펜션 #아울렛 #소품샵",

                "담양 프로방스"
            ),
            Recommend(
                "https://a.travel-assets.com/findyours-php/viewfinder/images/res70/249000/249021-Chinatown-Incheon.jpg",

                "한국의 중국",

                "차이나타운",

                "#인천 #중구 #중화요리 #탕후루 #자유공원\n #동화마을 #신포국제시장 #월미바다열차",

                "인천 차이나타운"
            ),
            Recommend(
                "https://www.asan.go.kr/tour/upload_data/m_tour/blue_city_03.jpg",

                "한국의 프랑스",

                "지중해 마을",

                "#충남 #아산 #산토리니 #프로방스 #파르테논\n #레스토랑 #카페 #문화체험",

                "아산 지중해마을"
            ),
            Recommend(
                "https://modo-phinf.pstatic.net/20211229_62/1640748755625FK6qW_JPEG/mosaqHBtXV.jpeg?type=a1100",

                "한국의 일본",

                "니지모리 스튜디오",

                "#경기 #동두천 #료칸 #스시 #라멘 #사케\n #카페 #잡화점 #의상실 #사진관 #고양이",

                "니지모리 스튜디오"
            ),


            )
    }


}