package net.ScyllaMc.Matan.Rank;

import net.ScyllaMc.Matan.MelonCore.MelonCore;

public class loadPerms {

	@SuppressWarnings("static-access")
	public static void loadRankPerms() {

		MelonCore.PermissionManager.default_rank.add("bending.admin.rechoose");
		MelonCore.PermissionManager.default_rank.add("bending.air");
		MelonCore.PermissionManager.default_rank.add("bending.fire");
		MelonCore.PermissionManager.default_rank.add("bending.water");
		MelonCore.PermissionManager.default_rank.add("bending.earth");
		MelonCore.PermissionManager.default_rank.add("bending.chi");
		MelonCore.PermissionManager.default_rank.add("bending.command.rechoose");
		MelonCore.PermissionManager.default_rank.add("bending.command.choose");
		MelonCore.PermissionManager.default_rank.add("bending.ability.WaterCombo");
		MelonCore.PermissionManager.default_rank.add("bending.ability.FireCombo");
		MelonCore.PermissionManager.default_rank.add("bending.ability.AirCombo");
		MelonCore.PermissionManager.default_rank.add("bending.ability.EarthCombo");
		MelonCore.PermissionManager.default_rank.add("bending.ability.Link");
		MelonCore.PermissionManager.default_rank.add("bending.ability.firedisc");
		MelonCore.PermissionManager.default_rank.add("bending.ability.waterrestore");
		MelonCore.PermissionManager.default_rank.add("bending.earth.sandbending");
		MelonCore.PermissionManager.default_rank.add("bending.earth.metalbending");
		MelonCore.PermissionManager.default_rank.add("bending.earth.lavabending");
		MelonCore.PermissionManager.default_rank.add("bending.fire.lightningbending");
		MelonCore.PermissionManager.default_rank.add("bending.fire.combustionbending");
		MelonCore.PermissionManager.default_rank.add("bending.water.icebending");
		MelonCore.PermissionManager.default_rank.add("bending.water.plantbending");
		MelonCore.PermissionManager.default_rank.add("bending.water.healing");
		MelonCore.PermissionManager.default_rank.add("bending.ability.MetalClips");
		MelonCore.PermissionManager.default_rank.add("bending.ability.MetalClips.*");
		
		MelonCore.PermissionManager.gold_rank.add("server.gold");
		MelonCore.PermissionManager.gold_rank.add("server.avatar");
		
		MelonCore.PermissionManager.hero_rank.add("server.hero");
		MelonCore.PermissionManager.hero_rank.add("bending.water.bloodbending");
		MelonCore.PermissionManager.hero_rank.add("bending.water.bloodbending.anytime");
		MelonCore.PermissionManager.hero_rank.add("bending.ability.Bloodbending");
		MelonCore.PermissionManager.hero_rank.add("bending.ability.Bloodbending.anytime");
		MelonCore.PermissionManager.hero_rank.add("server.fly");

		MelonCore.PermissionManager.master_rank.add("server.master");
		MelonCore.PermissionManager.master_rank.add("server.speed");
		MelonCore.PermissionManager.master_rank.add("bending.ability.AvatarState");
		MelonCore.PermissionManager.master_rank.add("bending.ability.ElementSphere");
		MelonCore.PermissionManager.master_rank.add("bending.ability.ElementSphere.Air");
		MelonCore.PermissionManager.master_rank.add("bending.ability.ElementSphere.Fire");
		MelonCore.PermissionManager.master_rank.add("bending.ability.ElementSphere.Earth");
		MelonCore.PermissionManager.master_rank.add("bending.ability.ElementSphere.Water");
		MelonCore.PermissionManager.master_rank.add("bending.ability.ElementSphere.Stream");
		MelonCore.PermissionManager.master_rank.add("bending.water.bloodbending");
		MelonCore.PermissionManager.master_rank.add("bending.water.bloodbending.anytime");
		MelonCore.PermissionManager.master_rank.add("bending.ability.bloodbending");
		
		MelonCore.PermissionManager.champion_rank.add("server.champion");
		MelonCore.PermissionManager.champion_rank.add("server.workbench");
		MelonCore.PermissionManager.champion_rank.add("server.enderchest");
		MelonCore.PermissionManager.champion_rank.add("server.heal");
		MelonCore.PermissionManager.champion_rank.add("server.god");
		MelonCore.PermissionManager.champion_rank.add("server.itemdb");

		MelonCore.PermissionManager.vip_rank.add("server.teleport");
		
		MelonCore.PermissionManager.jrmod_rank.add("server.jrmod");
		MelonCore.PermissionManager.jrmod_rank.add("server.fly");

		MelonCore.PermissionManager.mod_rank.add("server.mod");
		MelonCore.PermissionManager.mod_rank.add("server.hide");
		MelonCore.PermissionManager.mod_rank.add("server.god");
		MelonCore.PermissionManager.mod_rank.add("server.heal");
		MelonCore.PermissionManager.mod_rank.add("server.clear");
		MelonCore.PermissionManager.mod_rank.add("server.teleport");
		MelonCore.PermissionManager.mod_rank.add("server.hide");
		
		MelonCore.PermissionManager.srmod_rank.add("server.invsee");
		MelonCore.PermissionManager.srmod_rank.add("server.weather");
		MelonCore.PermissionManager.srmod_rank.add("server.gamemode");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.adminclaims");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.claimslistother");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.ignoreclaims");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.deleteclaims");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.createclaims");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.transferclaim");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.adjustclaimblocks");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.eavesdrop");
		MelonCore.PermissionManager.srmod_rank.add("griefprevention.overrideclaimcountlimit");

		MelonCore.PermissionManager.admin_rank.add("minecraft.command.weather");
		MelonCore.PermissionManager.admin_rank.add("minecraft.command.time");
		MelonCore.PermissionManager.admin_rank.add("minecraft.*");
		MelonCore.PermissionManager.admin_rank.add("bukkit.*");
		MelonCore.PermissionManager.admin_rank.add("server.give");
		MelonCore.PermissionManager.admin_rank.add("holograms.*");
		MelonCore.PermissionManager.admin_rank.add("server.admin");
		MelonCore.PermissionManager.admin_rank.add("plots.admin");
		MelonCore.PermissionManager.admin_rank.add("worldedit.*");
		MelonCore.PermissionManager.admin_rank.add("worldguard.*");
		MelonCore.PermissionManager.admin_rank.add("voxelsniper.*");
		MelonCore.PermissionManager.admin_rank.add("nocheatplus.*");
		MelonCore.PermissionManager.admin_rank.add("melon.*");
		MelonCore.PermissionManager.admin_rank.add("BungeePortals.command.BPortals");
		MelonCore.PermissionManager.admin_rank.add("nocheatplus.command.*");
		MelonCore.PermissionManager.admin_rank.add("citizens.admin.*");
		MelonCore.PermissionManager.admin_rank.add("citizens.npc.*");
		MelonCore.PermissionManager.admin_rank.add("citizens.*");
		MelonCore.PermissionManager.admin_rank.add("server.heal.others");
		MelonCore.PermissionManager.admin_rank.add("server.*");
		MelonCore.PermissionManager.admin_rank.add("server.nick");
		MelonCore.PermissionManager.admin_rank.add("griefprevention*");
		MelonCore.PermissionManager.admin_rank.add("server.give.others");
		MelonCore.PermissionManager.admin_rank.add("holograms.create");
		MelonCore.PermissionManager.admin_rank.add("holograms.delete");
		MelonCore.PermissionManager.admin_rank.add("holograms.near");
		MelonCore.PermissionManager.admin_rank.add("holograms.edit");
		MelonCore.PermissionManager.admin_rank.add("holograms.addline");
		MelonCore.PermissionManager.admin_rank.add("holograms.removeline");
		MelonCore.PermissionManager.admin_rank.add("holograms.setline");
		MelonCore.PermissionManager.admin_rank.add("holograms.insertline");
		MelonCore.PermissionManager.admin_rank.add("holograms.movehere");
		MelonCore.PermissionManager.admin_rank.add("holograms.info");
		MelonCore.PermissionManager.admin_rank.add("holograms.copy");
		MelonCore.PermissionManager.admin_rank.add("holograms.reload");
		MelonCore.PermissionManager.admin_rank.add("holograms.fix");
		MelonCore.PermissionManager.admin_rank.add("holograms.list");
		MelonCore.PermissionManager.admin_rank.add("holograms.help");
		MelonCore.PermissionManager.admin_rank.add("holograms.teleport");

		try {
			MelonCore.PermissionManager.gold_rank.addAll(MelonCore.PermissionManager.default_rank);
			MelonCore.PermissionManager.hero_rank.addAll(MelonCore.PermissionManager.gold_rank);
			MelonCore.PermissionManager.master_rank.addAll(MelonCore.PermissionManager.hero_rank);
			MelonCore.PermissionManager.champion_rank.addAll(MelonCore.PermissionManager.master_rank);
			MelonCore.PermissionManager.vip_rank.addAll(MelonCore.PermissionManager.champion_rank);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			MelonCore.PermissionManager.jrmod_rank.addAll(MelonCore.PermissionManager.default_rank);
			MelonCore.PermissionManager.mod_rank.addAll(MelonCore.PermissionManager.gold_rank);
			MelonCore.PermissionManager.mod_rank.addAll(MelonCore.PermissionManager.jrmod_rank);
			MelonCore.PermissionManager.srmod_rank.addAll(MelonCore.PermissionManager.mod_rank);
			MelonCore.PermissionManager.admin_rank.addAll(MelonCore.PermissionManager.srmod_rank);
			MelonCore.PermissionManager.admin_rank.addAll(MelonCore.PermissionManager.champion_rank);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
