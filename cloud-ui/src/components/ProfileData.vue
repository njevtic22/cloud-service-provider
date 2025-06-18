<template>
    <v-row no-gutters>
        <v-col cols="4" class="d-flex flex-column align-center">
            <v-img
                src="/src/assets/user-logo.png"
                alt="Profile image"
                class="profile-image"
            >
            </v-img>
        </v-col>
        <v-col cols="8">
            <v-card-text>
                <v-row>
                    <v-col>
                        <v-text-field label="Name" variant="underlined">
                        </v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field label="Surname" variant="underlined">
                        </v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field label="Email" variant="underlined">
                        </v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field label="Username" variant="underlined">
                        </v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field label="Role" variant="underlined">
                        </v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field label="Organization" variant="underlined">
                        </v-text-field>
                    </v-col>
                </v-row>
                <v-btn color="primary">Chevron</v-btn>
            </v-card-text>
        </v-col>
    </v-row>
</template>

<script setup>
//
</script>

<style scoped>
.profile-image {
    width: 50%;
}
</style>
