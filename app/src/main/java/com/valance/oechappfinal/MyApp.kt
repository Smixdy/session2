package com.valance.oechappfinal

import android.app.Application
import com.valance.oechappfinal.databinding.ActivityMainBinding
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod

class MyApp: Application() {
    lateinit var supabaseClient: SupabaseClient

    override fun onCreate() {
        super.onCreate()
        supabaseClient = createSupabaseClient(
            supabaseUrl = "https://tprnayptxrlhorhzhpos.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRwcm5heXB0eHJsaG9yaHpocG9zIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTAxNDE4NzQsImV4cCI6MjAyNTcxNzg3NH0.EqWuE_PSzQ0WMfHidLsH9YrdO8smvxd_OIhXTDbplMg"
        ) {
            install(Postgrest){
                defaultSchema = "public"
                propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME
            }
        }
    }

}