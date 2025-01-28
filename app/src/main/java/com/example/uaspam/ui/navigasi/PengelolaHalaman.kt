package com.example.uaspam.ui.navigasi

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.uaspam.ui.view.Buku.BukuHomeScreen
import com.example.uaspam.ui.view.Buku.DestinasiBukuHome
import com.example.uaspam.ui.view.Buku.DestinasiDetail
import com.example.uaspam.ui.view.Buku.DestinasiInsertBuku
import com.example.uaspam.ui.view.Buku.DestinasiUpdate
import com.example.uaspam.ui.view.Buku.DetailBukuScreen
import com.example.uaspam.ui.view.Buku.InsertBukuScreen
import com.example.uaspam.ui.view.Buku.UpdateBukuScreen
import com.example.uaspam.ui.view.Home.DestinasiHome
import com.example.uaspam.ui.view.Home.HomeAppView
import com.example.uaspam.ui.view.Kategori.KategoriHomeScreen
import com.example.uaspam.ui.view.Kategori.DestinasiKategoriHome
import com.example.uaspam.ui.view.Kategori.DestinasiDetailKategori
import com.example.uaspam.ui.view.Kategori.DestinasiInsertKategori
import com.example.uaspam.ui.view.Kategori.DestinasiUpdateKategori
import com.example.uaspam.ui.view.Kategori.DetailKategoriScreen
import com.example.uaspam.ui.view.Kategori.InsertKategoriScreen
import com.example.uaspam.ui.view.Kategori.UpdateKategoriScreen
import com.example.uaspam.ui.view.Penerbit.DestinasiDetailPenerbit
import com.example.uaspam.ui.view.Penerbit.DestinasiInsertPenerbit
import com.example.uaspam.ui.view.Penerbit.DestinasiPenerbitHome
import com.example.uaspam.ui.view.Penerbit.DestinasiUpdatePenerbit
import com.example.uaspam.ui.view.Penerbit.DetailPenerbitScreen
import com.example.uaspam.ui.view.Penerbit.InsertPenerbitScreen
import com.example.uaspam.ui.view.Penerbit.PenerbitHomeScreen
import com.example.uaspam.ui.view.Penerbit.UpdatePenerbitScreen
import com.example.uaspam.ui.view.Penulis.DestinasiDetailPenulis
import com.example.uaspam.ui.view.Penulis.DestinasiInsertPenulis
import com.example.uaspam.ui.view.Penulis.DestinasiPenulisHome
import com.example.uaspam.ui.view.Penulis.DestinasiUpdatePenulis
import com.example.uaspam.ui.view.Penulis.DetailPenulisScreen
import com.example.uaspam.ui.view.Penulis.InsertPenulisScreen
import com.example.uaspam.ui.view.Penulis.PenulisHomeScreen
import com.example.uaspam.ui.view.Penulis.UpdatePenulisScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PengelolaHalaman() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHome.route) {
            HomeAppView(
                onBukuClick = { navController.navigate(DestinasiBukuHome.route) },
                onKategoriClick = { navController.navigate(DestinasiKategoriHome.route) },
                onPenerbitClick = { navController.navigate(DestinasiPenerbitHome.route) },
                onPenulisClick = { navController.navigate(DestinasiPenulisHome.route) },
            )
        }

        composable(DestinasiKategoriHome.route) {
            KategoriHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertKategori.route) },
                onDetailClick = { idKategori ->
                    navController.navigate("${DestinasiDetailKategori.route}/$idKategori")
                },
                navigateToBuku = { navController.navigate(DestinasiBukuHome.route) },
                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },
                navigateToPenerbit = { navController.navigate(DestinasiPenerbitHome.route) },
                navigateToPenulis = { navController.navigate(DestinasiPenulisHome.route) },
            )
        }
        composable(DestinasiInsertKategori.route) {
            InsertKategoriScreen(
                navigateBack = {
                    navController.navigate(DestinasiKategoriHome.route) {
                        popUpTo(DestinasiKategoriHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetailKategori.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKategori.ID_KATEGORI) {
                    type = NavType.IntType
                }
            )
        ) {
            val idKategori = it.arguments?.getInt(DestinasiDetailKategori.ID_KATEGORI)
            idKategori?.let {
                DetailKategoriScreen(
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdateKategori.route}/$idKategori")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiKategoriHome.route) {
                            popUpTo(DestinasiKategoriHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiUpdateKategori.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateKategori.ID_KATEGORI) {
                    type = NavType.IntType
                }
            )
        ) {
            val idKategori = it.arguments?.getInt(DestinasiUpdateKategori.ID_KATEGORI)
            idKategori?.let {
                UpdateKategoriScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        composable(DestinasiBukuHome.route) {
            BukuHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertBuku.route) },
                onDetailClick = { idBuku ->
                    navController.navigate("${DestinasiDetail.route}/$idBuku")
                },
                navigateToBuku = { navController.navigate(DestinasiBukuHome.route) },
                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },
                navigateToPenerbit = { navController.navigate(DestinasiPenerbitHome.route) },
                navigateToPenulis = { navController.navigate(DestinasiPenulisHome.route) },
            )
        }
        composable(DestinasiInsertBuku.route) {
            InsertBukuScreen(
                navigateBack = {
                    navController.navigate(DestinasiBukuHome.route) {
                        popUpTo(DestinasiBukuHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.ID_BUKU) {
                    type = NavType.IntType
                }
            )
        ) {
            val idBuku = it.arguments?.getInt(DestinasiDetail.ID_BUKU)
            idBuku?.let {
                DetailBukuScreen(
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdate.route}/$idBuku")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiBukuHome.route) {
                            popUpTo(DestinasiBukuHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.ID_BUKU) {
                    type = NavType.StringType
                }
            )
        ) {
            val idBuku = it.arguments?.getInt(DestinasiUpdate.ID_BUKU)
            idBuku?.let {
                UpdateBukuScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }


                )
            }
        }
        composable(
            DestinasiPenerbitHome.route,
            deepLinks = listOf(navDeepLink { uriPattern = "android-app://androidx.navigation/penerbit_home" })
        ) {
            PenerbitHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPenerbit.route) },
                onDetailClick = { idPenerbit ->
                    navController.navigate("${DestinasiDetailPenerbit.route}/$idPenerbit")
                },
                navigateToBuku = { navController.navigate(DestinasiBukuHome.route) },
                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },
                navigateToPenerbit = { navController.navigate(DestinasiPenerbitHome.route) },
                navigateToPenulis = { navController.navigate(DestinasiPenulisHome.route) },
            )
        }

        composable(DestinasiInsertPenerbit.route) {
            InsertPenerbitScreen(
                navigateBack = {
                    navController.navigate(DestinasiPenerbitHome.route) {
                        popUpTo(DestinasiPenerbitHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetailPenerbit.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPenerbit.ID_PENERBIT) {
                    type = NavType.IntType
                }
            )
        ) {
            val idPenerbit = it.arguments?.getInt(DestinasiDetailPenerbit.ID_PENERBIT)
            idPenerbit?.let {
                DetailPenerbitScreen(
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdatePenerbit.route}/$idPenerbit")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiPenerbitHome.route) {
                            popUpTo(DestinasiPenerbitHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiUpdatePenerbit.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePenerbit.ID_PENERBIT) {
                    type = NavType.IntType
                }
            )
        ) {
            val idPenerbit = it.arguments?.getInt(DestinasiUpdatePenerbit.ID_PENERBIT)
            idPenerbit?.let {
                UpdatePenerbitScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        composable(DestinasiPenulisHome.route) {
            PenulisHomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPenulis.route) },
                onDetailClick = { idPenulis ->
                    navController.navigate("${DestinasiDetailPenulis.route}/$idPenulis")
                },
                navigateToBuku = { navController.navigate(DestinasiBukuHome.route) },
                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },
                navigateToPenerbit = { navController.navigate(DestinasiPenerbitHome.route) },
                navigateToPenulis = { navController.navigate(DestinasiPenulisHome.route) },
            )
        }
        composable(DestinasiInsertPenulis.route) {
            InsertPenulisScreen(
                navigateBack = {
                    navController.navigate(DestinasiPenulisHome.route) {
                        popUpTo(DestinasiPenulisHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            DestinasiDetailPenulis.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPenulis.ID_PENULIS) {
                    type = NavType.IntType
                }
            )
        ) {
            val idPenulis = it.arguments?.getInt(DestinasiDetailPenulis.ID_PENULIS)
            idPenulis?.let {
                DetailPenulisScreen(
                    navigateToItemUpdate = {
                        navController.navigate("${DestinasiUpdatePenulis.route}/$idPenulis")
                    },
                    navigateBack = {
                        navController.navigate(DestinasiPenulisHome.route) {
                            popUpTo(DestinasiPenulisHome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiUpdatePenulis.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePenulis.ID_PENULIS) {
                    type = NavType.IntType
                }
            )
        ) {
            val idPenulis = it.arguments?.getInt(DestinasiUpdatePenulis.ID_PENULIS)
            idPenulis?.let {
                UpdatePenulisScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}